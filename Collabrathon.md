# EHRbase Demo

### IPS Data

IPS Template contains sections for:
* Medication summary
* Allergies and Intolerances
* Problem List
* Immunizations
* History of Procedures
* Medical Devices
* Diagnostic Results
* Vital Signs
* Past History of Illnesses
* Pregnancy
* Social History
* Plan of Care
* Functional Status
* Advanced Directives

### Sample AQL queries

Medication summary:

```text
SELECT f/items[at0132]/value AS Name,
       f/items[at0071]/value AS Form,
       p/items[at0001] AS Specific_date,
       p/items[at0002]/value AS Repetition_interval
FROM EHR e[ehr_id/value='d973be47-b04b-4fd9-b0c8-f7d84db14d34'] -- choose from the above
CONTAINS COMPOSITION c[openEHR-EHR-COMPOSITION.health_summary.v1] 
CONTAINS ACTION d[openEHR-EHR-ACTION.medication.v1] 
CONTAINS (CLUSTER f[openEHR-EHR-CLUSTER.medication.v1] and CLUSTER p[openEHR-EHR-CLUSTER.timing_nondaily.v1]) 
OFFSET 0 LIMIT 10
```

Allergies and Intolerances:

```text
SELECT x/data[at0001]/items[at0002]/value AS substance,
       x/data[at0001]/items[at0063,'Verification status']/value AS Verification_status,
       x/data[at0001]/items[at0101]/value AS Criticality,
       x/data[at0001]/items[at0006]/value AS Comment,
       x/data[at0001]/items[at0009,'Reaction']/items[at0027,'Onset']/value AS Onset,
       x/protocol[at0042]/items[at0062]/value AS Last_updated
FROM EHR e[ehr_id/value='d973be47-b04b-4fd9-b0c8-f7d84db14d34'] -- choose from the above
CONTAINS COMPOSITION c[openEHR-EHR-COMPOSITION.health_summary.v1] 
CONTAINS EVALUATION x[openEHR-EHR-EVALUATION.adverse_reaction_risk.v1] 
OFFSET 0 LIMIT 10
```

Problem List:

```text
SELECT d/data[at0001]/items[at0002]/value AS problem_diagnosis_name,
       d/data[at0001]/items[at0012]/value AS Body_site,
       d/data[at0001]/items[at0077]/value AS Date_time_of_onset,
       d/data[at0001]/items[at0005]/value AS Severity,
       d/data[at0001]/items[at0073]/value AS Diagnostic_certainty
FROM EHR e[ehr_id/value='d973be47-b04b-4fd9-b0c8-f7d84db14d34'] -- choose from the above
CONTAINS COMPOSITION c[openEHR-EHR-COMPOSITION.health_summary.v1] 
CONTAINS EVALUATION d[openEHR-EHR-EVALUATION.problem_diagnosis.v1] 
OFFSET 0 LIMIT 10
```

