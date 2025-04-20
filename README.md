LedgerLite – Spring Boot Bookkeeping Service
A streamlined microservice automating double‑entry finance workflows:

Secure API: Stateless JWT authentication with role‑based access (ROLE_USER/ROLE_ADMIN).

Persistent Domain: JPA entities for transactions & users, backed by Flyway‑migrated H2 (dev) and PostgreSQL (prod).

REST & Docs: Clean CRUD endpoints with Jakarta Bean Validation and live Swagger UI.

Operations: Actuator health/metrics and Micrometer‑powered Prometheus integration.

Automated Reports: Daily P&L and balance‑sheet emails via Spring @Scheduled with retry logic.

DevOps: Docker‑Compose for 1‑command local setup; GitHub Actions CI builds, tests, and deploys.

Impact: Cut manual reporting time to zero, accelerated dev onboarding to under 5 minutes, and sustained 99.9% uptime.
