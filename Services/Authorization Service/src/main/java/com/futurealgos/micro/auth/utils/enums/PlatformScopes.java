/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.auth.utils.enums;

/**
 * Package: com.futurealgos.micro.auth.utils.enums
 * Project: Prasad Psycho
 * Created On: 19/08/22
 * Created By: Animeshh Parashar
 * animeshh@futurealgos.com
 **/
public enum PlatformScopes {
    PROFILE("profile", "Allow access to User Information."),
    EXAMINEE_READ("examinee.read", "Allow access to information related to Examinees and Groups created under the Partner"),
    EXAMINEE_WRITE("examinee.write", "Allow creating or modifying Examinees and Groups"),
    ASSESSMENT_READ("assessment.read", "Allow access to information related to Assessment Requests created under the Partner"),
    ASSESSMENT_WRITE("assessment.write", "Allow creating new Assessment Requests"),
    REPORTS_READ("reports.read", "Allow access to reports generated for Examinee under any Assessment Request"),
    REPORTS_WRITE("reports.write", "Allow generating reports for assessments given by any examinee"),
    LIBRARY_READ("library.read", "Allow access to Library"),
    DOCS_READ("docs.read", "Allow access to Documents related to Partner"),
    DOCS_WRITE("docs.write", "Allow adding and modifying Documents related to Partner"),
    MESSAGES_READ("messages.read", "Allow access to Messages related to Partner"),
    MESSAGES_WRITE("messages.write", "Allow sending messages under channels created for each Partner"),
    NOTIFICATIONS_READ("notifications.read", "Allow access to notifications"),
    TEST_RESTRICTED_READ("notifications.write", "Allow access to Test Marketplace"),
    LIBRARY_WRITE("library.write", "Allow adding and modifying documents to Library"),
    TEST_READ("tests.read", "Allow access to information related to Tests created on the Platform"),
    TEST_WRITE("tests.write", "Allow creating new Tests, Norms, Categories as well as ability to modify existing information"),
    PARTNER_READ("partners.read", "Allow access to information related to Partners created on the Platform"),
    PARTNER_WRITE("partners.write", "Allow modifying existing Partners"),
    ADMIN_READ("admin.read", "Allow access to information related to Admin created on the Platform"),
    ADMIN_WRITE("admin.write", "Allow adding and modifying Administrators created on the Platform");

    private final String tag;
    private final String description;

    PlatformScopes(String tag, String description) {
        this.tag = tag;
        this.description = description;
    }

    public static PlatformScopes fromTag(String tag) {
        for (PlatformScopes scope : values()) {
            if (scope.tag.equals(tag)) {
                return scope;
            }
        }

        throw new IllegalStateException("No scope found for tag: " + tag);
    }


    public String tag() {
        return tag;
    }

    public String description() {
        return description;
    }
}
