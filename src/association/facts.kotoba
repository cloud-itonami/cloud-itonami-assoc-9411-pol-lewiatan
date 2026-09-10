(ns association.facts
  "Industry rule/history catalog for Konfederacja Lewiatan (Polish
  Confederation Lewiatan) -- a 70th industry-association-level source
  (see cloud-itonami-assoc-9411-sau-fsc, -9411-aut-wko, -9411-irl-ibec,
  -9411-nzl-businessnz, -9411-cze-spcr, -9411-ind-cii, -9411-zaf-busa,
  -9411-bra-cni, -9411-ken-kam, -9411-can-chamber, -9411-mex-coparmex,
  -9411-ita-confindustria, -9411-nld-vnoncw, -9411-kor-kcci,
  -9411-arg-uia, -9411-bel-feb, -9411-dnk-di, -9411-swe-sn, -9411-fin-ek,
  -9411-tha-fti, -9411-chl-sofofa, -9411-col-andi, -9411-cri-uccaep,
  -9411-ecu-cip, -9411-egy-fei, -9411-pry-uip, -9411-ury-ciu for the
  first twenty-seven) per ADR-2607141700 (cloud-itonami-compliance-
  fact-federation). The TWENTY-EIGHTH entry aligned to ISIC 9411
  (activities of business, employers, and professional membership
  organizations). Fills Poland's previously-open association-axis gap
  -- one of the 5 countries (GTM/HND/PAN/POL/PRT) that reached
  association-only-missing status once the municipality axis's
  structural gaps were fully closed at tick 167. Poland now has real,
  individually verified facts across ALL THREE axes (country:
  cloud-itonami-iso3166-pol statute.facts; municipality:
  cloud-itonami-municipality-pol-warsaw; association: this entry).

  Every entry carries the verbatim span its claim rests on
  (`:association-rule/source-quote`), the page that span was read from,
  and how precisely that page dates the fact
  (`:association-rule/date-precision`). This is what makes the catalog
  checkable rather than merely cited: scripts/verify-catalog.cljs
  --live fetches each source and requires the span to still be there,
  and its structural pass requires the span to actually NAME the date
  the entry claims. A URL that resolves and does not support the claim
  looks exactly like one that does.

  All ten entries are the organisation speaking about itself, except
  the last, which is BusinessEurope's own member directory
  corroborating the membership Lewiatan asserts on its own site --
  recorded separately, with its own provenance keyword, rather than
  folded into Lewiatan's claim.

  Dates are recorded at the precision the source gives and no finer.
  Six entries are year-precise because lewiatan.org states them that
  way; three are precise to the day; one carries no date at all and
  says so in `:date-unknown-because` rather than being quietly
  undated. A THIRD candidate founding date -- the modern
  organization's own founding in January 1999 -- is still NOT a
  catalog entry: pl.wikipedia.org gives month-only precision and
  lewiatan.org's own pages do not restate it, so it stays in
  organization.edn for context. The bar is the source's own words, not
  the date's availability.

  An association not in `catalog` has NO spec-basis, full stop; never
  fabricate one.")

(def catalog
  "association-slug -> vector of association-rule entries."
  {"lewiatan"
   [{:association-rule/id "lewiatan.centralny-zwiazek-founding-1919-12-15"
     :association-rule/title "Centralny Związek Polskiego Przemysłu, Górnictwa, Handlu i Finansów (Lewiatan's pre-war predecessor) founded 15 December 1919"
     :association-rule/association "lewiatan"
     :association-rule/isic "9411"
     :association-rule/country "POL"
     :association-rule/kind :governance-program
     :association-rule/url "https://lewiatan.org/100-lat-lewiatana/"
     :association-rule/url-provenance :official-lewiatan-org
     :association-rule/source-article "100 lat Lewiatana"
     :association-rule/source-quote "15 grudnia został powołany do życia Centralny Związek Polskiego Przemysłu, Górnictwa, Handlu i Finansów."
     :association-rule/established-date "1919-12-15"
     :association-rule/date-precision :day
     :association-rule/retrieved-at "2026-09-06"
     :association-rule/topic #{:governance}}
    {:association-rule/id "lewiatan.tradition-adopted-2004-10-28"
     :association-rule/title "Polska Konfederacja Pracodawców Prywatnych took over the pre-war Centralny Związek's tradition and adopted the 'Lewiatan' name on 28 October 2004"
     :association-rule/association "lewiatan"
     :association-rule/isic "9411"
     :association-rule/country "POL"
     :association-rule/kind :governance-program
     :association-rule/url "https://lewiatan.org/100-lat-lewiatana/"
     :association-rule/url-provenance :official-lewiatan-org
     :association-rule/source-article "100 lat Lewiatana"
     :association-rule/source-quote "28 października 2004 r. Polska Konfederacja Pracodawców Prywatnych przejęła tradycję przedwojennego Centralnego Związku"
     :association-rule/established-date "2004-10-28"
     :association-rule/date-precision :day
     :association-rule/retrieved-at "2026-09-06"
     :association-rule/topic #{:governance}}
    {:association-rule/id "lewiatan.przeglad-gospodarczy-first-issue-1920-04-01"
     :association-rule/title "First issue of the Związek's own biweekly Przegląd Gospodarczy appeared 1 April 1920"
     :association-rule/association "lewiatan"
     :association-rule/isic "9411"
     :association-rule/country "POL"
     :association-rule/kind :governance-program
     :association-rule/url "https://lewiatan.org/100-lat-lewiatana/"
     :association-rule/url-provenance :official-lewiatan-org
     :association-rule/source-article "100 lat Lewiatana"
     :association-rule/source-quote "1 kwietnia 1920 wyszedł pierwszy numer organu Związku, dwutygodnika „Przegląd Gospodarczy”."
     :association-rule/established-date "1920-04-01"
     :association-rule/date-precision :day
     :association-rule/retrieved-at "2026-09-06"
     :association-rule/topic #{:publications}}
    {:association-rule/id "lewiatan.name-shortened-2015"
     :association-rule/title "Organisation's name shortened in 2015 to today's Konfederacja Lewiatan"
     :association-rule/association "lewiatan"
     :association-rule/isic "9411"
     :association-rule/country "POL"
     :association-rule/kind :governance-program
     :association-rule/url "https://lewiatan.org/100-lat-lewiatana/"
     :association-rule/url-provenance :official-lewiatan-org
     :association-rule/source-article "100 lat Lewiatana"
     :association-rule/source-quote "W 2015 roku nazwę organizacji skrócono i dzisiaj jest znana jako Konfederacja Lewiatan."
     :association-rule/established-date "2015"
     :association-rule/date-precision :year
     :association-rule/retrieved-at "2026-09-06"
     :association-rule/topic #{:governance}}
    {:association-rule/id "lewiatan.wierzbicki-award-since-2004"
     :association-rule/title "Lewiatan has awarded the annual Nagroda im. Andrzeja Wierzbickiego to entrepreneurs since 2004"
     :association-rule/association "lewiatan"
     :association-rule/isic "9411"
     :association-rule/country "POL"
     :association-rule/kind :governance-program
     :association-rule/url "https://lewiatan.org/100-lat-lewiatana/"
     :association-rule/url-provenance :official-lewiatan-org
     :association-rule/source-article "100 lat Lewiatana"
     :association-rule/source-quote "Od 2004 r. Lewiatan przyznaje doroczną Nagrodę im. Andrzeja Wierzbickiego"
     :association-rule/established-date "2004"
     :association-rule/date-precision :year
     :association-rule/retrieved-at "2026-09-06"
     :association-rule/topic #{:awards}}
    {:association-rule/id "lewiatan.businesseurope-member-since-2002"
     :association-rule/title "Lewiatan has been Poland's only employers' organisation in BusinessEurope since 2002"
     :association-rule/association "lewiatan"
     :association-rule/isic "9411"
     :association-rule/country "POL"
     :association-rule/kind :governance-program
     :association-rule/url "https://lewiatan.org/businesseurope/"
     :association-rule/url-provenance :official-lewiatan-org
     :association-rule/source-article "BusinessEurope"
     :association-rule/source-quote "Konfederacja Lewiatan jako jedyna polska organizacja pracodawców jest członkiem BusinessEurope od 2002 roku"
     :association-rule/established-date "2002"
     :association-rule/date-precision :year
     :association-rule/retrieved-at "2026-09-06"
     :association-rule/topic #{:european-representation}}
    {:association-rule/id "lewiatan.brussels-office-since-2001"
     :association-rule/title "Lewiatan has maintained a permanent Brussels office since 2001, as the only representative Polish employers' organisation to do so"
     :association-rule/association "lewiatan"
     :association-rule/isic "9411"
     :association-rule/country "POL"
     :association-rule/kind :governance-program
     :association-rule/url "https://lewiatan.org/lewiatan-w-brukseli/"
     :association-rule/url-provenance :official-lewiatan-org
     :association-rule/source-article "Lewiatan w Brukseli"
     :association-rule/source-quote "Jako jedyna reprezentatywna organizacja pracodawców z Polski posiadamy na stałe biuro w Brukseli, które działa od 2001 roku."
     :association-rule/established-date "2001"
     :association-rule/date-precision :year
     :association-rule/retrieved-at "2026-09-06"
     :association-rule/topic #{:european-representation}}
    {:association-rule/id "lewiatan.business-at-oecd-member-since-2023"
     :association-rule/title "Lewiatan has been a member of Business at OECD (BIAC) since 2023"
     :association-rule/association "lewiatan"
     :association-rule/isic "9411"
     :association-rule/country "POL"
     :association-rule/kind :governance-program
     :association-rule/url "https://lewiatan.org/business-at-oecd-biac/"
     :association-rule/url-provenance :official-lewiatan-org
     :association-rule/source-article "Business at OECD (BIAC)"
     :association-rule/source-quote "Od 2023 roku Konfederacja Lewiatan jest jej członkiem"
     :association-rule/established-date "2023"
     :association-rule/date-precision :year
     :association-rule/retrieved-at "2026-09-06"
     :association-rule/topic #{:international-representation}}
    {:association-rule/id "lewiatan.social-dialogue-council-participant"
     :association-rule/title "Lewiatan takes part in the Rada Dialogu Społecznego as a representative employers' organisation"
     :association-rule/association "lewiatan"
     :association-rule/isic "9411"
     :association-rule/country "POL"
     :association-rule/kind :governance-program
     :association-rule/url "https://lewiatan.org/rada-dialogu-spolecznego/"
     :association-rule/url-provenance :official-lewiatan-org
     :association-rule/source-article "Rada Dialogu Społecznego"
     :association-rule/source-quote "Konfederacja Lewiatan uczestniczy w pracach RDS jako reprezentatywna organizacja pracodawców."
     :association-rule/date-unknown-because :page-states-no-date
     :association-rule/retrieved-at "2026-09-06"
     :association-rule/topic #{:social-dialogue}}
    {:association-rule/id "lewiatan.businesseurope-direct-member-federation"
     :association-rule/title "BusinessEurope's own member directory lists Polish Confederation Lewiatan among the national business federations that are its direct members"
     :association-rule/association "lewiatan"
     :association-rule/isic "9411"
     :association-rule/country "POL"
     :association-rule/kind :governance-program
     :association-rule/url "https://www.businesseurope.eu/member/polish-confederation-lewiatan/"
     :association-rule/url-provenance :official-businesseurope-eu
     :association-rule/source-article "Member – Poland: Polish Confederation Lewiatan"
     :association-rule/source-quote "We speak for enterprises of all sizes in 36 European countries whose national business federations are our direct members."
     :association-rule/date-unknown-because :page-states-no-date
     :association-rule/retrieved-at "2026-09-06"
     :association-rule/topic #{:european-representation}}]
})

(defn spec-basis [association] (get catalog association))

(defn coverage
  ([] (coverage (keys catalog)))
  ([associations]
   (let [have (filter catalog associations)
         missing (remove catalog associations)]
     {:requested (count associations)
      :covered (count have)
      :covered-associations (vec (sort have))
      :missing-associations (vec (sort missing))
      :note (str "cloud-itonami-assoc-9411-pol-lewiatan Wave 0 (ADR-2607141700): "
                 (count (get catalog "lewiatan")) " Lewiatan entries, each citing a "
                 "primary source with the page and the verbatim span it rests on. "
                 "Extend `association.facts/catalog`, never fabricate an id/url.")})))

(defn by-topic [association topic]
  (filterv #(contains? (:association-rule/topic %) topic) (spec-basis association)))

(defn sources
  "Distinct pages this catalog rests on, with the entries each carries.
   A catalog that cites one page is one redesign away from citing none;
   this is the number that says how exposed it is."
  ([] (sources "lewiatan"))
  ([association]
   (->> (spec-basis association)
        (group-by :association-rule/url)
        (reduce-kv (fn [m url es]
                     (assoc m url (mapv :association-rule/id es)))
                   {}))))

(defn undated
  "Entries that carry no date, with the reason the source gives none.
   Being undated is a fact about the source and is recorded, not hidden."
  ([] (undated "lewiatan"))
  ([association]
   (->> (spec-basis association)
        (filterv #(nil? (:association-rule/established-date %)))
        (mapv (juxt :association-rule/id :association-rule/date-unknown-because)))))
