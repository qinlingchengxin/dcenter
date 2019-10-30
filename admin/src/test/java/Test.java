import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

/**
 * User: LiWenC
 * Date: 17-11-17
 */
public class Test {
    public static void main(String[] args) throws IOException, InterruptedException {
        table();
    }

    public static void table() throws IOException, InterruptedException {
        String[] tables = {"ASSESSMENT_BID",
                "ASSESSMENT_BID_GCJS",
                "AVOIDANCE_EXPERT",
                "AVOIDANCE_UNIT",
                "BID_CLARIFY",
                "BID_CLARIFY_GCJS",
                "BID_EXCEPTION_REPORT",
                "BID_EXCEPTION_REPORT_GCJS",
                "BID_QUESTION",
                "BID_QUESTION_GCJS",
                "BID_SECTION",
                "BID_SECTION_GCJS",
                "BIDDER_LIST",
                "BIDDER_LIST_GCJS",
                "COMMUNICATE_SUPERVISE",
                "COMMUNICATE_SUPERVISE_PROJECT",
                "CONTRACT",
                "CONTRACT_PERFORMANCE",
                "EXPERTS_EXTRACT",
                "EXTRACTION_CONDITION",
                "EXTRACTION_OPERATION",
                "INVITATION_FOR_BID",
                "INVITATION_FOR_BID_GCJS",
                "INVITING_UNIT",
                "INVITING_UNIT_GCJS",
                "MEETING_INFO",
                "MEETING_INFO_GCJS",
                "MEMBER_INFO",
                "MEMBER_INFO_GCJS",
                "OBJECTION_MANAGE",
                "OBJECTION_MANAGE_GCJS",
                "OPEN_BID",
                "OPEN_BID_GCJS",
                "PUBLICTITY_COMPLAIN",
                "PURCHASE_BULLETIN",
                "PURCHASE_BULLETIN_RECORD",
                "PURCHASE_DOC",
                "PURCHASE_PROJECT",
                "PURCHASE_RESULT_INFO",
                "QUALIFICATION_BULLETIN",
                "QUALIFICATION_CLARIFY",
                "QUALIFICATION_CLARIFY_JSGC",
                "QUALIFICATION_DOC",
                "QUALIFICATION_QUESTION",
                "QUALIFICATION_QUESTION_GCJS",
                "QUALIFICATION_RESULT_INFO",
                "QUALIFICATION_RESULT_INFO_GCJS",
                "QUALIFICATION_REVIEW_SITUATION",
                "QUALIFICATION_REVIEW_SITUATION_GCJS",
                "TENDER_BULLETIN",
                "TENDER_DOC",
                "TENDER_PROJECT",
                "WIN_BID_CANDIDATE_BULLETIN",
                "WIN_BID_CANDIDATER",
                "WIN_BID_NOTICE",
                "WIN_BID_NOTICE_GCJS",
                "WIN_BID_RESULT_INFO",
                "ZHUTIJIBENXINXI"};
        String sql = "INSERT INTO `ETL_ENTITY` (`ID`, `PRJ_ID`, `SRC_TAB_NAME`, `DES_TAB_NAME`, `SRC_PRIMARY_KEY`, `DES_PRIMARY_KEY`, `DESCRIPTION`, `ETL_ID`, `CREATE_TIME`) VALUES ('%s', '2f56be7e-ea7c-4ed1-9853-9632c07d7b98', '%s', '%s', 'GUID', 'GUID', '', '%s', %s);\n";
        FileWriter fileWriter = new FileWriter("e:/entity.sql");

        for (String table : tables) {
            Thread.sleep(10);
            fileWriter.write(String.format(sql, UUID.randomUUID().toString(), table, table, System.currentTimeMillis(), System.currentTimeMillis()));
            fileWriter.flush();
        }
        fileWriter.close();
    }
}
