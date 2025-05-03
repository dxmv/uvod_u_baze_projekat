-- 1. How many sessions has each therapist held in 2025 so far?
SELECT k.kandidatId,
       k.ime,
       k.prezime,
       COUNT(*) AS total_sessions
FROM Seansa s
JOIN Kandidat k ON k.kandidatId = s.fk_kandidatId
WHERE s.datum >= '2025-01-01'
  AND s.datum <  '2026-01-01'
GROUP BY k.kandidatId
ORDER BY total_sessions DESC;

-- 2. Therapist revenue leaderboard for last month
WITH last_month AS (
  SELECT DATE_FORMAT(CURRENT_DATE() - INTERVAL 1 MONTH, '%Y-%m') AS ym
)
SELECT k.kandidatId,
       k.ime,
       k.prezime,
       SUM(p.iznos) AS revenue
FROM Placanje p
JOIN Seansa  s ON s.seansaId     = p.Seansa_seansaId
JOIN Kandidat k ON k.kandidatId  = s.fk_kandidatId
JOIN last_month lm
     ON DATE_FORMAT(p.datum,'%Y-%m') = lm.ym
GROUP BY k.kandidatId
ORDER BY revenue DESC;

-- 3. Clients who haven’t had a session in the last 6 months
SELECT kl.klijentId,
       kl.ime,
       kl.prezime,
       MAX(s.datum) AS last_session
FROM Klijent kl
LEFT JOIN Seansa s ON s.fk_klijentId = kl.klijentId
GROUP BY kl.klijentId
HAVING MAX(s.datum) IS NULL
    OR MAX(s.datum) < CURRENT_DATE() - INTERVAL 6 MONTH;
