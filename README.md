# cloud-itonami-assoc-9411-pol-lewiatan

Industry rule/history catalog for **Konfederacja Lewiatan** (Polish
Confederation Lewiatan) — the TWENTY-EIGHTH entry aligned to **ISIC
9411** (activities of business, employers, and professional
membership organizations), alongside
[`-9411-ury-ciu`](https://github.com/cloud-itonami/cloud-itonami-assoc-9411-ury-ciu)
(Uruguay),
[`-9411-pry-uip`](https://github.com/cloud-itonami/cloud-itonami-assoc-9411-pry-uip)
(Paraguay), and 26 other national industry/employers associations.
Part of the [`cloud-itonami`](https://github.com/cloud-itonami)
compliance-fact family (ADR-2607141700,
`cloud-itonami-compliance-fact-federation`, in `com-junkawasaki/root`).

Fills one of the 5 countries (GTM/HND/PAN/POL/PRT) that reached
association-only-missing status once the municipality axis's
structural gaps were fully closed at tick 167. Poland now has real,
individually verified facts across **all three axes** (country,
municipality, association).

## Sourcing note

Both dates are directly confirmed by reading `lewiatan.org`'s own
official "100 lat Lewiatana" (100 years of Lewiatan) history page:
the pre-war predecessor (Centralny Związek Polskiego Przemysłu,
Górnictwa, Handlu i Finansów) founded 15 December 1919, and the
modern organization's adoption of the "Lewiatan" name/tradition on 28
October 2004. A third candidate date — the modern organization's own
1999 founding — was checked via `pl.wikipedia.org` but only at
month precision and not independently corroborated by `lewiatan.org`
itself, so it is documented in `organization.edn` for context rather
than promoted to its own catalog entry.

## Scope

A **read-only reference/archive** catalog — not an Advisor⊣Governor
actuation actor. It proposes or executes nothing on Lewiatan's behalf.

Coverage is reported honestly (see `association.facts/coverage`): an
association not in `catalog` has **no spec-basis**, full stop — never
fabricate one.

## Data

- `src/association/facts.cljc` — the catalog, source of truth.
- `schema/association-rule.edn` — DataScript schema.
- `data/datascript-tx.edn` — derived DataScript tx-data (query this
  alongside other `cloud-itonami`/`etzhayyim` compliance-fact sources via
  `com-junkawasaki/root`'s `scripts/compliance-fact-query.cljs`).

## License

AGPL-3.0-or-later (matches the `cloud-itonami-iso3166-*` /
`-municipality-*` / `-assoc-*` / `-lei-*` convention).
