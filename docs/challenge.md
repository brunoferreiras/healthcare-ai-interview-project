# Candidate Challenge

**Duration**: ~90 minutes  
**Stack**: Java 21, Spring Boot 3, PostgreSQL, Kafka

You may use any AI tool (ChatGPT, Claude, Copilot, Gemini, etc.). When you do:
- State which tool you used and what you asked
- Explain whether you accepted, rejected, or modified the suggestion and why
- Do not apply suggestions you cannot explain

Start the infrastructure before coding:
```
docker-compose up -d
./mvnw spring-boot:run
```

---

## Module 1 — Architecture Discovery (15 min)

Walk through the request flow for `POST /appointments` end-to-end.

Answer the following:
1. What is each layer's responsibility? Where does business logic belong?
2. What happens if `AppointmentService` is called directly, bypassing the controller?
3. What does `updateStatus` return if you pass `status=INVALID_VALUE`? Is that acceptable?
4. Identify at least **two risks** in the current implementation without touching any code.

---

## Module 2 — Feature Development (25 min)

The current `GET /appointments` loads every row in the database. With 1M records, this will cause an OutOfMemoryError.

Implement the following on the existing endpoint:

1. **Pagination** using Spring's `Pageable` (not manual offset/limit)
2. **Filtering** by `status` and `doctorId` (both optional, combinable)
3. **Input validation**: invalid `status` values must return HTTP 400, not 500
4. **At least one unit test** for the service layer
5. **At least one integration test** using Testcontainers (not H2)

Expected response shape:
```json
{
  "content": [...],
  "page": 0,
  "size": 20,
  "totalElements": 1000000,
  "totalPages": 50000
}
```

Commit your changes incrementally.

---

## Module 3 — Code Review (15 min)

Review `AiGeneratedAppointmentService.java`. This was submitted as a PR by a colleague who used an AI assistant to generate it.

Answer the following:
1. Would you approve this PR? Why or why not?
2. List every issue you find. For each one, state the severity (critical / major / minor) and explain the impact.
3. For each critical or major issue, show how you would fix it.

---

## Module 4 — Incident Investigation (15 min)

Read `docs/incident-report.md`.

Answer the following:
1. What are your top two root-cause hypotheses?
2. How would you confirm or rule out each hypothesis **without touching production**?
3. What is your immediate mitigation — right now, while the incident is still active?
4. What is the permanent fix?
5. What process or technical change would prevent this class of bug in the future?

---

## Module 5 — Performance Analysis (10 min)

Review `OrderLikeService.java`.

1. What is wrong with the current `findAppointments` implementation?
2. Rewrite it so it does not load unnecessary data into memory.
3. What database index would you add, and why?
4. How would you verify the fix improved performance?

---

## Module 6 — AI Usage Reflection (10 min)

Across all modules where you used AI assistance:

1. Show one prompt you wrote verbatim. How did you decide what context to include?
2. Was the AI suggestion correct? How did you verify it — test, documentation, manual reasoning?
3. Describe one case where you modified or rejected an AI suggestion. What was the issue?
4. In your view, what separates a strong AI-assisted developer from a weak one?
