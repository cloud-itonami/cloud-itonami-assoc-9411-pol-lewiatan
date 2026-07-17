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

  Both dates directly confirmed by reading lewiatan.org's own official
  '100 lat Lewiatana' (100 years of Lewiatan) history page: the
  pre-war predecessor, Centralny Związek Polskiego Przemysłu,
  Górnictwa, Handlu i Finansów, was founded 15 December 1919 ('15
  grudnia 1919 roku został powołany do życia Centralny Związek
  Polskiego Przemysłu, Górnictwa, Handlu i Finansów'); and the modern
  Polska Konfederacja Pracodawców Prywatnych took over that pre-war
  tradition and adopted the 'Lewiatan' name on 28 October 2004 ('28
  października 2004 r. Polska Konfederacja Pracodawców Prywatnych
  przejęła tradycję przedwojennego Centralnego Związku...i dołączyła
  do swojej nazwy słowo Lewiatan'). A THIRD candidate date -- the
  modern organization's own founding in January 1999 -- was checked
  via pl.wikipedia.org ('utworzona w stycznia 1999') but NOT included
  as a separate catalog entry here: pl.wikipedia.org gives month-only
  precision and lewiatan.org's own history page does not independently
  restate this specific date, so it is documented in organization.edn
  for context rather than promoted to a spec-basis entry of its own
  (both catalog entries below are precise-to-the-day and sourced
  directly from the organization's own primary history page).

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
     :association-rule/established-date "1919-12-15"
     :association-rule/retrieved-at "2026-07-18"
     :association-rule/topic #{:governance}}
    {:association-rule/id "lewiatan.tradition-adopted-2004-10-28"
     :association-rule/title "Polska Konfederacja Pracodawców Prywatnych took over the pre-war Centralny Związek's tradition and adopted the 'Lewiatan' name on 28 October 2004"
     :association-rule/association "lewiatan"
     :association-rule/isic "9411"
     :association-rule/country "POL"
     :association-rule/kind :governance-program
     :association-rule/url "https://lewiatan.org/100-lat-lewiatana/"
     :association-rule/url-provenance :official-lewiatan-org
     :association-rule/established-date "2004-10-28"
     :association-rule/retrieved-at "2026-07-18"
     :association-rule/topic #{:governance}}]})

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
                 (count (get catalog "lewiatan")) " Lewiatan entries seeded "
                 "with lewiatan.org's own official '100 lat Lewiatana' history page. "
                 "Extend `association.facts/catalog`, never fabricate an id/url.")})))

(defn by-topic [association topic]
  (filterv #(contains? (:association-rule/topic %) topic) (spec-basis association)))
