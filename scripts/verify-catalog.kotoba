#!/usr/bin/env nbb
;; scripts/verify-catalog.cljs — check the Lewiatan catalog against its own
;; sources.
;;
;;   nbb scripts/verify-catalog.cljs            structural only (offline)
;;   nbb scripts/verify-catalog.cljs --live     also fetch every :url and require
;;                                              every :source-quote to be in it
;;
;; Ported from cloud-itonami-assoc-9411-nzl-businessnz, minus the check that
;; catalog needs and this one does not, plus two this one needs and it does not
;; (see :provenance-host and :date-not-in-quote below).
;;
;; Exit codes are three-valued on purpose:
;;
;;   0  checked, nothing wrong
;;   1  checked, findings printed
;;   2  REFUSED -- could not check. Not 0, because "I could not read the
;;      catalog" and "I read the catalog and it was fine" must not leave the
;;      same trace, and not 1, because there is no finding to act on.
;;
;; Why :source-quote exists at all: reachability is not support. A URL that
;; returns HTTP 200 and does not contain the claim looks exactly like a URL
;; that does, so a citation can rot without anyone noticing. --live does not
;; ask whether the citation resolves; it asks whether the document still says
;; the thing the entry says it says.
;;
;; Why :date-not-in-quote exists here specifically: a quote being present is
;; still not support for THIS entry. Nine of these ten entries are dated, the
;; dates are the whole point of a history catalog, and the source writes them
;; in Polish words ("15 grudnia", "1 kwietnia 1920") rather than in ISO. So a
;; wrong :established-date -- a transcription slip, or a date carried over from
;; a neighbouring entry -- would leave the quote check green: the span really
;; is on the page, it just does not say what the entry claims. This check reads
;; the Polish date out of the span and requires it to be the date recorded.
;;
;; Why :provenance-host exists here specifically: unlike the BusinessNZ
;; catalog, this association's own site answers, so every claim can be sourced
;; from the organisation itself -- and that is exactly what makes an
;; :official-... provenance keyword worth checking. The keyword asserts WHO is
;; speaking; only the URL's host can corroborate it, and nothing otherwise
;; stops a third-party page from being labelled official.

(ns verify-catalog
  (:require [clojure.edn :as edn]
            [kotoba.lang.text :as str]
            ["fs" :as fs]
            ["os" :as os]
            ["path" :as path]
            ["child_process" :as cp]))

;; process.argv holds this script's own path. Dropping a fixed count gets it
;; wrong the moment the launcher changes, and the symptom is that the script
;; path becomes the catalog path -- which this script then reports as
;; unreadable, i.e. a refusal that looks like a broken catalog.
(def argv (vec (remove #(str/ends-with? % "verify-catalog.cljs")
                       (drop 2 (js->clj (.-argv js/process))))))
(def live? (some #{"--live"} argv))
(def data-path
  (or (first (remove #(str/starts-with? % "--") argv)) "data/datascript-tx.edn"))

(def ASSOCIATION "lewiatan")
(def ISIC "9411")
(def COUNTRY "POL")

;; A provenance keyword names WHO is speaking. The host is the only thing in
;; the entry that can corroborate it, so the two are declared together here and
;; an unlisted keyword is a finding rather than a pass -- a new source has to
;; say whose site it is, instead of inheriting the word "official" for free.
(def provenance->host
  {:official-lewiatan-org      "lewiatan.org"
   :official-businesseurope-eu "businesseurope.eu"})

;; Polish dates are written in words, and the month is in the genitive.
(def pl-months
  ["stycznia" "lutego" "marca" "kwietnia" "maja" "czerwca"
   "lipca" "sierpnia" "września" "października" "listopada" "grudnia"])

(defn refuse! [msg]
  (println (str "REFUSED: " msg))
  (println "Refusing to report a pass on a catalog this run could not read.")
  (.exit js/process 2))

(defn- read-catalog []
  (let [txt (try (fs/readFileSync data-path "utf8")
                 (catch :default e (refuse! (str data-path ": " (.-message e)))))
        data (try (edn/read-string txt)
                  (catch :default e (refuse! (str data-path " is not readable EDN: "
                                                 (.-message e)))))]
    (when-not (vector? data)
      (refuse! (str data-path " is not a vector of entries (got "
                    (if (nil? data) "nil" (type data)) ")")))
    (when (empty? data)
      ;; An empty catalog satisfies every per-entry assertion below. Without
      ;; this floor, deleting the catalog would be reported as a clean run.
      (refuse! (str data-path " holds no entries; every per-entry check would "
                    "be vacuously true")))
    [txt data]))

(def date-re #"^\d{4}(-\d{2})?(-\d{2})?$")

(defn- precision-of [d]
  (case (count (str/split (str d) #"-")) 3 :day 2 :month 1 :year nil))

(defn- host-of [u]
  (when-let [m (re-matches #"^https://([^/]+)/.*$" (str u))]
    (str/replace (nth m 1) #"^www\." "")))

(defn- date-named-in?
  "Does `q` name `d` at `prec`, the way this source writes dates? ISO is
   accepted too, so a source that does write ISO is not forced into Polish."
  [q d prec]
  (let [q (str/lower q)
        [y m dd] (str/split (str d) #"-")
        month (when m (get pl-months (dec (js/parseInt m 10))))
        ;; "01" is written "1"; both spellings are accepted.
        day-forms (when dd (distinct [dd (str (js/parseInt dd 10))]))]
    (or (str/includes? q (str/lower (str d)))
        (case prec
          :day (boolean (some #(str/includes? q (str % " " month)) day-forms))
          :month (str/includes? q month)
          :year (str/includes? q y)
          false))))

(defn- structural [data]
  (let [ids (map :association-rule/id data)
        dups (->> ids frequencies (keep (fn [[k n]] (when (< 1 n) k))) sort)]
    (concat
     (for [d dups] [:duplicate-id (str d " appears " (count (filter #{d} ids)) " times")])
     (mapcat
      (fn [[i e]]
        (let [at (fn [k] (get e (keyword "association-rule" k)))
              where (str "entry " i " (" (or (at "id") "<no id>") ")")
              f (fn [tag msg] [tag (str where ": " msg)])]
          (concat
           (when-not (string? (at "id")) [(f :missing-key ":id is missing or not a string")])
           (when (and (string? (at "id"))
                      (not (str/starts-with? (at "id") (str ASSOCIATION "."))))
             [(f :id-shape (str ":id must start with \"" ASSOCIATION ".\""))])
           (when-not (and (string? (at "title")) (seq (at "title")))
             [(f :missing-key ":title is missing or empty")])
           (when-not (= ASSOCIATION (at "association"))
             [(f :missing-key (str ":association must be " ASSOCIATION))])
           (when-not (= ISIC (at "isic")) [(f :missing-key (str ":isic must be " ISIC))])
           (when-not (= COUNTRY (at "country")) [(f :missing-key (str ":country must be " COUNTRY))])
           (when-not (keyword? (at "kind")) [(f :missing-key ":kind must be a keyword")])
           (when-not (and (string? (at "url")) (str/starts-with? (at "url") "https://"))
             [(f :url-shape ":url must be an https:// URL")])
           (when-not (keyword? (at "url-provenance"))
             [(f :missing-key ":url-provenance must be a keyword")])
           ;; The provenance keyword and the URL must name the same speaker.
           (when (keyword? (at "url-provenance"))
             (let [p (at "url-provenance")
                   expected (provenance->host p)
                   h (host-of (at "url"))]
               (cond
                 (nil? expected)
                 [(f :provenance-host
                     (str p " is not a declared provenance; add it to "
                          "provenance->host with the host it speaks for, or use "
                          "one of: " (str/join ", " (sort (map str (keys provenance->host))))))]
                 (nil? h)
                 [(f :provenance-host (str "cannot read a host out of :url " (at "url")))]
                 (not (or (= h expected) (str/ends-with? h (str "." expected))))
                 [(f :provenance-host
                     (str p " claims " expected " but :url is served by " h))]
                 :else nil)))
           (when-not (and (string? (at "source-article")) (seq (at "source-article")))
             [(f :missing-key ":source-article is missing or empty")])
           (when-not (and (string? (at "source-quote")) (seq (at "source-quote")))
             [(f :missing-key (str ":source-quote is missing or empty -- an entry with "
                                   "no quote cannot be checked against its own source"))])
           (when-not (and (vector? (at "topic")) (seq (at "topic")))
             [(f :missing-key ":topic must be a non-empty vector")])
           (for [d [(at "established-date") (at "last-revised-date") (at "retrieved-at")]
                 :when (and (some? d) (not (re-matches date-re (str d))))]
             (f :date-shape (str "not an ISO date: " d)))
           ;; A date the source does not give may be omitted -- but only out
           ;; loud. An entry that is simply missing both dates and one that
           ;; records why it has none must not read the same.
           (when-not (or (at "established-date") (at "last-revised-date")
                         (at "date-unknown-because"))
             [(f :missing-key (str "needs :established-date or :last-revised-date, "
                                   "or :date-unknown-because naming why the source "
                                   "gives neither"))])
           (when (and (at "date-unknown-because")
                      (not (keyword? (at "date-unknown-because"))))
             [(f :missing-key ":date-unknown-because must be a keyword")])
           (when (and (at "date-unknown-because")
                      (or (at "established-date") (at "last-revised-date")))
             [(f :date-shape (str ":date-unknown-because is set on an entry that "
                                  "does carry a date"))])
           ;; How precisely the source dates the fact is a property of the
           ;; source, not of the reader. This catalog's own README turned a
           ;; candidate date away for being month-precise; recording precision
           ;; per entry is what lets that judgement be checked instead of
           ;; recounted in prose.
           (let [d (or (at "established-date") (at "last-revised-date"))
                 p (at "date-precision")]
             (cond
               (and (nil? d) p)
               [(f :date-precision ":date-precision is set on an entry with no date")]
               (and d (nil? p))
               [(f :date-precision (str "needs :date-precision (" (name (precision-of d))
                                        ") saying how precisely the source dates it"))]
               (and d p (not= p (precision-of d)))
               [(f :date-precision (str ":date-precision " p " does not match " d
                                        " (" (name (precision-of d)) ")"))]
               ;; The quote must name the date. A span that is on the page but
               ;; does not carry this date supports some other entry, not this
               ;; one -- and reads identically until asked.
               (and d p (string? (at "source-quote"))
                    (not (date-named-in? (at "source-quote") d p)))
               [(f :date-not-in-quote
                   (str ":source-quote does not name " d " (" (name p)
                        "); the span is not evidence for this entry's date"
                        "\n      quote: " (at "source-quote")))]
               :else nil)))))
      (map-indexed vector data)))))

(def ua "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120 Safari/537.36")

(defn- have? [bin]
  (try (cp/execFileSync "sh" #js ["-c" (str "command -v " bin)] #js {:stdio "ignore"}) true
       (catch :default _ false)))

;; Named entities this decoder knows. It is deliberately not the whole HTML5
;; table: what matters here is the Latin-1 letters and the punctuation a source
;; in this family is made of. An incomplete decoder does not weaken this check,
;; it inverts it -- the verbatim span of a page that IS serving the claim comes
;; back as not found, which is exactly how a fabricated citation looks. Polish
;; letters (ą ć ę ł ń ó ś ź ż) are served as UTF-8 by lewiatan.org rather than
;; as entities, so they arrive through .toString below and need no table.
(def ^:private named-entities
  {"nbsp" " " "quot" "\"" "apos" "'" "lt" "<" "gt" ">"
   "aacute" "á" "eacute" "é" "iacute" "í" "oacute" "ó" "uacute" "ú"
   "Aacute" "Á" "Eacute" "É" "Iacute" "Í" "Oacute" "Ó" "Uacute" "Ú"
   "agrave" "à" "egrave" "è" "igrave" "ì" "ograve" "ò" "ugrave" "ù"
   "acirc" "â" "ecirc" "ê" "icirc" "î" "ocirc" "ô" "ucirc" "û"
   "auml" "ä" "euml" "ë" "iuml" "ï" "ouml" "ö" "uuml" "ü"
   "ntilde" "ñ" "atilde" "ã" "otilde" "õ" "ccedil" "ç" "Ccedil" "Ç"
   "oacute;" "ó" "sect" "§" "deg" "°" "middot" "·"
   "laquo" "«" "raquo" "»" "bdquo" "„" "ldquo" "“" "rdquo" "”"
   "lsquo" "‘" "rsquo" "’" "sbquo" "‚"
   "ndash" "–" "mdash" "—" "hellip" "…" "euro" "€" "pound" "£"})

(defn- decode-entities
  "HTML entities -> characters. `&amp;` is decoded LAST, so that a document
   that literally writes `&amp;aacute;` keeps saying `&aacute;` rather than
   silently becoming an accented letter."
  [s]
  (-> s
      (str/replace #"&#(\d+);"
                   (fn [[_ d]] (js/String.fromCodePoint (js/parseInt d 10))))
      (str/replace #"&#[xX]([0-9a-fA-F]+);"
                   (fn [[_ h]] (js/String.fromCodePoint (js/parseInt h 16))))
      (str/replace #"&([a-zA-Z][a-zA-Z0-9]{1,9});"
                   (fn [[whole nm]] (get named-entities nm whole)))
      (str/replace #"&amp;" "&")))

(defn- fetch-text
  "Returns [status text] or [status nil] -- nil text means the body arrived but
   this run could not turn it into text, which is a refusal, not a finding."
  [url]
  (let [tmp (path/join (os/tmpdir) (str "lewiatan-src-" (hash url)))
        status (try (str/trim (str (cp/execFileSync
                                    "curl" #js ["-sS" "-L" "--max-time" "120"
                                                "-A" ua "-o" tmp
                                                "-w" "%{http_code}" url]
                                    #js {:encoding "utf8"})))
                    (catch :default e (str "curl-failed: " (.-message e))))
        body (try (fs/readFileSync tmp) (catch :default _ nil))
        pdf? (and body (str/starts-with? (.toString (.slice body 0 5) "utf8") "%PDF-"))
        text (cond
               (nil? body) nil
               pdf? (when (have? "pdftotext")
                      (try (str (cp/execFileSync "pdftotext" #js [tmp "-"]
                                                 #js {:encoding "utf8"
                                                      :maxBuffer 33554432}))
                           (catch :default _ nil)))
               :else (-> (.toString body "utf8")
                         (str/replace #"(?is)<(script|style|noscript)[^>]*>.*?</\1>" " ")
                         (str/replace #"(?s)<[^>]+>" " ")
                         decode-entities))]
    (try (fs/unlinkSync tmp) (catch :default _ nil))
    [status (when text (str/replace text #"\s+" " "))]))

(defn- run-live [data]
  (when-not (have? "curl") (refuse! "curl is not on PATH"))
  (let [urls (vec (distinct (map :association-rule/url data)))
        fetched (reduce (fn [m u] (assoc m u (fetch-text u))) {} urls)
        unreadable (for [[u [status text]] fetched
                         :when (or (not (re-matches #"2\d\d" status)) (nil? text))]
                     (str u " -> status=" status
                          (when (nil? text) (str " (body could not be turned into text"
                                                 (when-not (have? "pdftotext")
                                                   "; pdftotext is not on PATH")
                                                 ")"))))]
    (println (str "FETCHED\t" (- (count urls) (count unreadable)) "/" (count urls)))
    (when (seq unreadable)
      ;; Every quote check below would be "not found", which reads exactly like
      ;; a fabricated citation. Refuse instead of accusing the catalog.
      (refuse! (str "could not read " (count unreadable) " of " (count urls)
                    " sources:\n  " (str/join "\n  " unreadable))))
    (keep (fn [e]
            (let [u (:association-rule/url e)
                  q (str/replace (str (:association-rule/source-quote e)) #"\s+" " ")
                  [_ text] (get fetched u)]
              (when-not (str/includes? text q)
                [:quote-not-in-source
                 (str (:association-rule/id e) ": :source-quote is not in " u
                      "\n      quote: " q)])))
          data)))

(let [[txt data] (read-catalog)
      findings (concat (structural data) (when live? (run-live data)))]
  (println (str "SCANNED\t" (count data) " entries, "
                (count (re-seq #"https?://" txt)) " citations, "
                (count (distinct (map :association-rule/url data))) " distinct sources"
                (if live? ", live" ", structural only")))
  (doseq [[tag msg] findings] (println (str "  [" (name tag) "] " msg)))
  (if (seq findings)
    (do (println (str (count findings) " finding(s)")) (.exit js/process 1))
    (do (println "ok") (.exit js/process 0))))
