package org.vaadin.example.datasource;

import com.google.common.collect.Streams;
import com.nedap.archie.rm.datavalues.DvText;
import com.nedap.archie.rm.ehr.EhrStatus;
import com.nedap.archie.rm.generic.PartySelf;
import com.nedap.archie.rm.support.identification.HierObjectId;
import com.nedap.archie.rm.support.identification.PartyRef;
import org.ehrbase.openehr.sdk.aql.dto.AqlQuery;
import org.ehrbase.openehr.sdk.aql.parser.AqlQueryParser;
import org.ehrbase.openehr.sdk.client.openehrclient.OpenEhrClient;
import org.ehrbase.openehr.sdk.client.openehrclient.OpenEhrClientConfig;
import org.ehrbase.openehr.sdk.generator.commons.aql.query.NativeQuery;
import org.ehrbase.openehr.sdk.generator.commons.aql.query.Query;
import org.ehrbase.openehr.sdk.generator.commons.aql.record.Record;
import org.ehrbase.openehr.sdk.generator.commons.interfaces.CompositionEntity;
import org.ehrbase.openehr.sdk.response.dto.QueryResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.vaadin.example.DateTimeUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class DataService {
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MMM.yyyy", Locale.UK);

    private final OpenEhrClient openEhrClient;

    @Value("${ehrbase.uri}")
    private String ehrbaseUri;
    @Value("${ehrId:}")
    private String ehrId;

    @Autowired
    public DataService(OpenEhrClient openEhrClient) {

        this.openEhrClient = openEhrClient;
    }


    public UUID createEHR(String external, String namespace) {

        if (external.isEmpty() || namespace.isEmpty()) {
            return openEhrClient.ehrEndpoint().createEhr();
        }

        EhrStatus status = new EhrStatus();

        PartyRef ref = new PartyRef();
        ref.setId(new HierObjectId(external));
        ref.setNamespace(namespace);
        ref.setType("PERSON");

        PartySelf partySelf = new PartySelf();
        partySelf.setExternalRef(ref);

        status.setSubject(partySelf);
        status.setModifiable(true);

        status.setArchetypeNodeId("openEHR-EHR-EHR_STATUS.generic.v1");
        status.setName(new DvText("test"));

        return openEhrClient.ehrEndpoint().createEhr(status);
    }

    public void sendComposition(CompositionEntity o){

        openEhrClient.compositionEndpoint(UUID.fromString(ehrId)).mergeCompositionEntity(o);
    }

    public List<BloodPressureSet> fetchBloodPressureEhrBase() {
        String aql = "SELECT o_67/data[at0001]/events[at0006]/data[at0003]/items[at0004]/value/magnitude, " +
                "o_67/data[at0001]/events[at0006]/data[at0003]/items[at0005]/value/magnitude, " +
                "o_67/data[at0001]/events[at0006]/time/value " +
                "FROM EHR e" + getEhrIdCondition() +
                "CONTAINS OBSERVATION o_67[openEHR-EHR-OBSERVATION.blood_pressure.v2]";

        return getResponseRowsEhrBase(aql).map(row -> new BloodPressureSet(
                DateTimeUtils.parseDateTime((String)row.get(2)),
                (Double)row.get(0),
                (Double)row.get(1))).sorted(Comparator.comparing(VitalsSet::getDateTime)).toList();
    }

    public List<BodyHeightSet> fetchBodyHeight() {
        String aql = "SELECT o_152/data[at0001]/events[at0002]/data[at0003]/items[at0004]/value/magnitude, " +
                "o_152/data[at0001]/events[at0002]/time/value " +
                "FROM EHR e" + getEhrIdCondition() +
                "CONTAINS OBSERVATION o_152[openEHR-EHR-OBSERVATION.height.v2] ";

        return getResponseRows(aql).map(row -> new BodyHeightSet(
                DateTimeUtils.parseDateTime((String)row.get(1)),
                (Double)row.get(0))).sorted(Comparator.comparing(VitalsSet::getDateTime)).toList();
    }

    public List<BodyHeightSet> fetchBodyHeightEhrBase() {
        String aql = "SELECT o_152/data[at0001]/events[at0002]/data[at0003]/items[at0004]/value/magnitude, " +
                "o_152/data[at0001]/events[at0002]/time/value " +
                "FROM EHR e" + getEhrIdCondition() +
                "CONTAINS OBSERVATION o_152[openEHR-EHR-OBSERVATION.height.v2] ";

        return getResponseRowsEhrBase(aql).map(row -> new BodyHeightSet(
                DateTimeUtils.parseDateTime((String)row.get(1)),
                (Double)row.get(0))).sorted(Comparator.comparing(VitalsSet::getDateTime)).toList();
    }


    public List<BodyWeightSet> fetchBodyWeight() {
        String aql = "SELECT " +
                "o_218/data[at0002]/events[at0003]/data[at0001]/items[at0004]/value/magnitude, " +
                "o_218/data[at0002]/events[at0003]/time/value " +
                "FROM EHR e" + getEhrIdCondition() +
                "CONTAINS OBSERVATION o_218[openEHR-EHR-OBSERVATION.body_weight.v2] ";

        return getResponseRows(aql).map(row -> new BodyWeightSet(
                DateTimeUtils.parseDateTime((String)row.get(1)),
                (Double)row.get(0))).sorted(Comparator.comparing(VitalsSet::getDateTime)).toList();
    }

    public List<BodyWeightSet> fetchBodyWeightEhrBase() {
        String aql = "SELECT " +
                "o_218/data[at0002]/events[at0003]/data[at0001]/items[at0004]/value/magnitude, " +
                "o_218/data[at0002]/events[at0003]/time/value " +
                "FROM EHR e" + getEhrIdCondition() +
                "CONTAINS OBSERVATION o_218[openEHR-EHR-OBSERVATION.body_weight.v2] ";

        return getResponseRowsEhrBase(aql).map(row -> new BodyWeightSet(
                DateTimeUtils.parseDateTime((String)row.get(1)),
                (Double)row.get(0))).sorted(Comparator.comparing(VitalsSet::getDateTime)).toList();
    }


    public List<DiagnosesSet> fetchDiagnoses() {
        String aql = "SELECT   " +
                "e_233/data[at0001]/items[at0002]/value/value,   " +
                "e_233/data[at0001]/items[at0077]/value/value,   " +
                "e_233/data[at0001]/items[openEHR-EHR-CLUSTER.problem_qualifier.v1]/items[at0003]/value/value, " +
                "e_233/data[at0001]/items[at0073]/value/value " +
                "FROM EHR e" + getEhrIdCondition() +
                "CONTAINS EVALUATION e_233[openEHR-EHR-EVALUATION.problem_diagnosis.v1]";

        return getResponseRows(aql).map(row -> {
            DiagnosesSet diagnosesSet = new DiagnosesSet();
            diagnosesSet.setDiagnosisName((String)row.get(0));
            diagnosesSet.setTimeOfOnset((String)row.get(1));
            diagnosesSet.setActive((String)row.get(2));
            diagnosesSet.setDiagnosisCertainty((String)row.get(3));
            return diagnosesSet;
        }).toList();
    }


    public List<VaccinationSet> fetchVaccination() {


        String aql = "SELECT j/description[at0017]/items[at0020,'Immunisation item']/value/value AS immunisation_item,\n" +
                     "       j/description[at0017]/items[at0140]/items[at0147]/value/value AS Route,\n" +
                     "       j/description[at0017]/items[at0140]/items[at0141]/value/value AS Target_site,\n" +
                     "       j/description[at0017]/items[at0025]/value/magnitude AS Sequence_number,\n" +
                     "       j/time/value AS time\n" +
                     "FROM EHR e" + getEhrIdCondition() +
                     "CONTAINS COMPOSITION c[openEHR-EHR-COMPOSITION.health_summary.v1]\n" +
                     "CONTAINS ACTION j[openEHR-EHR-ACTION.medication.v1]\n" +
                     "WHERE j/name/value = 'Immunization statement'\n";
        return        getResponseRowsEhrBase(aql).map(row -> new VaccinationSet((String)row.get(0),
                                                                                  (String)row.get(4),
                                                                                  (String)row.get(1),
                                                                                  (String)row.get(2),
                                                                                  getSequence(row.get(3)),
                                                                                  dateTimeFormatter.format(DateTimeUtils.parseDateTime((String)row.get(4))),
                                                                                  "vitagroup HIP")
                )
                .sorted(Comparator.comparing(VaccinationSet::getDate))
                .toList();
    }

    private String getSequence(Object value) {
        if (value == null) {
            return "";
        }
        return value.toString();
    }


    public List<MedicationSet> fetchMedication() {

        String aql = "SELECT f/items[at0132]/value/value AS Name,\n" +
                     "       f/items[at0071]/value/value AS Form,\n" +
                     "       p/items[at0001]/value/value AS Specific_date,\n" +
                     "       p/items[at0002]/value/value AS Repetition_interval\n" +
                     "FROM EHR e"  + getEhrIdCondition() +
                     "CONTAINS COMPOSITION c[openEHR-EHR-COMPOSITION.health_summary.v1] \n" +
                     "CONTAINS ACTION d[openEHR-EHR-ACTION.medication.v1] \n" +
                     "CONTAINS (CLUSTER f[openEHR-EHR-CLUSTER.medication.v1] and CLUSTER p[openEHR-EHR-CLUSTER.timing_nondaily.v1]) \n";
/*
        String aql = "SELECT inst/activities[at0001]/description[at0002]/items[at0070]/value/value, " +
                "direction/items[openEHR-EHR-CLUSTER.dosage.v2]/items[at0144]/value/units, " +
                "direction/items[openEHR-EHR-CLUSTER.dosage.v2]/items[at0144]/value/magnitude, " +
                "timing/items[at0003]/value/units, " +
                "timing/items[at0003]/value/magnitude " +
                "FROM EHR e" + getEhrIdCondition() +
                "CONTAINS " +
                "INSTRUCTION inst[openEHR-EHR-INSTRUCTION.medication_order.v3] CONTAINS " +
                "(CLUSTER direction[openEHR-EHR-CLUSTER.therapeutic_direction.v1] OR " +
                "CLUSTER timing [openEHR-EHR-CLUSTER.timing_daily.v1])";
*/

        return Streams.concat(
                getResponseRowsEhrBase(aql).map(row -> new MedicationSet((String)row.get(0),
                                                                         (String)row.get(1),
                                                                         (String)row.get(2),
                                                                         (String)row.get(3),
                                                                         "vitagroup HIP"))
        ).sorted(Comparator.comparing(med -> LocalDate.parse(med.getDate()))).toList();
    }

    private String getEhrIdCondition() {
        if (ehrId == null || ehrId.isBlank()) {
            return " ";
        } else {
            return "[ehr_id/value='" + ehrId + "'] ";
        }
    }

    private Stream<List<Object>> getResponseRows(String aql) {
        return getResponseRowsEhrBase(aql);
    }


    private Stream<List<Object>> getResponseRowsEhrBase(String aql) {
        AqlQuery aqlQuery = AqlQueryParser.parse(aql);
        NativeQuery<Record> query = Query.buildNativeQuery(aqlQuery.render());

        QueryResponseData queryResponseData = openEhrClient.aqlEndpoint().executeRaw(query);
        if (queryResponseData.getRows() == null) {
            return Stream.empty();
        } else {
            return queryResponseData.getRows().stream();
        }
    }
}





