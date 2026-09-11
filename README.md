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

## Sourcing

Ten entries across **six distinct pages**. Every entry records the
page it was read from, the verbatim span the claim rests on
(`:association-rule/source-quote`), and how precisely that page dates
the fact (`:association-rule/date-precision`).

Nine of the ten are the organisation speaking about itself, on
`lewiatan.org`. The tenth is BusinessEurope's own member directory
corroborating from the other side the membership Lewiatan asserts on
its own site — recorded as its own entry with its own provenance
keyword rather than folded into Lewiatan's claim.

| source | entries |
|---|---|
| `lewiatan.org/100-lat-lewiatana/` | founding 1919-12-15, name adopted 2004-10-28, *Przegląd Gospodarczy* 1920-04-01, name shortened 2015, Wierzbicki award since 2004 |
| `lewiatan.org/businesseurope/` | BusinessEurope member since 2002 |
| `lewiatan.org/lewiatan-w-brukseli/` | permanent Brussels office since 2001 |
| `lewiatan.org/business-at-oecd-biac/` | Business at OECD (BIAC) member since 2023 |
| `lewiatan.org/rada-dialogu-spolecznego/` | participates in the Rada Dialogu Społecznego as a representative employers' organisation |
| `businesseurope.eu/member/polish-confederation-lewiatan/` | listed among the national federations that are BusinessEurope's direct members |

**Dates are recorded at the precision the source gives and no finer.**
Three are precise to the day, six are year-precise because
`lewiatan.org` states them that way, and one carries no date at all
and says so in `:date-unknown-because` rather than being quietly
undated. A candidate founding date for the modern organization —
January 1999 — is still **not** an entry: `pl.wikipedia.org` gives
month-only precision and `lewiatan.org`'s own pages do not restate
it, so it stays in `organization.edn` for context. The bar is the
source's own words, not the date's availability.

## Checking the catalog

```bash
kbb --backend sci scripts/verify-catalog.cljk           # structural, offline
kbb --backend sci scripts/verify-catalog.cljk --live    # also fetch every source
```

`--live` does not ask whether each citation resolves; it asks whether
the document **still says the thing the entry says it says**. A URL
that returns 200 and no longer carries the claim is indistinguishable
from one that does, which is how a citation rots unnoticed.

The structural pass adds two checks this catalog needs:

- **`:date-not-in-quote`** — the span must actually *name* the date the
  entry claims. The sources write dates in Polish words (`15 grudnia`,
  `1 kwietnia 1920`), so a slipped `:established-date` would otherwise
  leave the quote check green: the span really is on the page, it just
  does not say what the entry claims.
- **`:provenance-host`** — an `:official-…` provenance keyword asserts
  *who is speaking*, and only the URL's host can corroborate it. A new
  source has to declare whose site it is instead of inheriting the word
  "official" for free.

Exit is three-valued: `0` checked and clean, `1` findings, `2`
**refused** — the run could not read the catalog or could not reach a
source. "I could not check" must not leave the same trace as "I
checked and it was fine".

## Scope

A **read-only reference/archive** catalog — not an Advisor⊣Governor
actuation actor. It proposes or executes nothing on Lewiatan's behalf.

Coverage is reported honestly (see `association.facts/coverage`): an
association not in `catalog` has **no spec-basis**, full stop — never
fabricate one. `organization.edn` records institutional titles only;
personal names of office-holders are never persisted here, and quotes
are chosen so they carry none.

## Data

- `src/association/facts.cljc` — the catalog, source of truth.
  `sources` reports which page each entry rests on; `undated` reports
  which entries carry no date and why.
- `schema/association-rule.edn` — DataScript schema.
- `data/datascript-tx.edn` — derived DataScript tx-data (query this
  alongside other `cloud-itonami`/`etzhayyim` compliance-fact sources via
  `com-junkawasaki/root`'s `scripts/compliance-fact-query.cljs`).
- `src/association_facts.kotoba` — the same catalog as Kotoba, reaching
  the oracle, wasm and both native ISAs. **Generated** by
  `kbb --backend sci scripts/gen-kotoba-port.cljk`; `--check` fails if it was
  hand-edited, and the parity suite compares every field of every entry
  against the `.cljc`.

## License

AGPL-3.0-or-later (matches the `cloud-itonami-iso3166-*` /
`-municipality-*` / `-assoc-*` / `-lei-*` convention).
