# Incident #143

**Severity**: P2  
**Detected**: 2024-03-15 09:42 UTC  
**Resolved**: 2024-03-15 14:10 UTC

---

## Symptoms

- Duplicate appointments appearing in the production database
- Approximately 5% duplication rate among appointments created since 07:00 UTC
- Patients receiving multiple confirmation emails for the same slot
- No errors or exceptions visible in the `appointment-service` logs

---

## Timeline

| Time (UTC) | Event |
|------------|-------|
| 07:00 | `appointment-reminders` Kafka consumer deployed and enabled for the first time |
| 07:05 | Consumer lag drops to zero; processing of backlogged messages begins |
| 08:30 | First patient complaint: duplicate confirmation emails |
| 09:42 | Alert fires: `appointments` table row count growing anomalously |
| 09:55 | On-call engineer disables the consumer |
| 10:00 | Duplication rate drops to 0% |
| 14:10 | Incident marked resolved (duplicates deduped manually) |

---

## Recent Changes

- `appointment-reminders` Kafka consumer enabled in `appointment-service` — first deployment to production
- The Kafka topic had **14,000 messages backlogged** from the prior 7 days (the consumer had never run before)
- No other changes to `appointment-service` in the past 72 hours
- No schema migrations were run

---

## Data

The following query returns 4,731 rows. All duplicates have `created_at` between 07:00–09:55 UTC:

```sql
SELECT patient_id, doctor_id, scheduled_at, COUNT(*) AS cnt
FROM appointments
GROUP BY patient_id, doctor_id, scheduled_at
HAVING COUNT(*) > 1
ORDER BY cnt DESC
LIMIT 20;
```

---

## Your Tasks

1. What are your top two root-cause hypotheses?
2. How would you confirm or rule out each hypothesis without touching production?
3. What is your immediate mitigation right now?
4. What is the permanent fix?
5. What process change would prevent this class of bug in the future?
