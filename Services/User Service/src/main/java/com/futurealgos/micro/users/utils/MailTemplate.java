/*******************************************************************************
 * Future Algorithms Private Limited
 * Copyright 2022 Future Algorithms Private Limited.
 * Unauthorized copying of any files, via any medium is strictly prohibited.
 * All Rights Reserved.
 ******************************************************************************/

package com.futurealgos.micro.users.utils;

public final class MailTemplate {

    public static final String CUSTOM_EMAIL_TEMPLATE =
            """
                    <!DOCTYPE html>
                    <html
                      lang="en"
                      xmlns:v="urn:schemas-microsoft-com:vml"
                      xmlns:o="urn:schemas-microsoft-com:office:office"
                    >
                      <head>
                        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
                        <meta name="x-apple-disable-message-reformatting" />
                        <meta http-equiv="x-ua-compatible" content="ie=edge" />
                        <meta name="viewport" content="width=device-width, initial-scale=1" />
                        <meta
                          name="format-detection"
                          content="telephone=no, date=no, address=no, email=no"
                        />
                        <!--[if mso]>
                          <xml
                            ><o:OfficeDocumentSettings
                              ><o:PixelsPerInch>96</o:PixelsPerInch></o:OfficeDocumentSettings
                            ></xml
                          >
                          <style>
                            td,
                            th,
                            div,
                            p,
                            a,
                            h1,
                            h2,
                            h3,
                            h4,
                            h5,
                            h6 {
                              font-family: "Segoe UI", sans-serif;
                              mso-line-height-rule: exactly;
                              text-align: justify;
                            }
                          </style>
                        <![endif]-->
                        <title>Verify Email Address</title>
                        <link href="./verify._files/css" rel="stylesheet" media="screen" />
                        <style>
                          .hover-underline:hover {
                            text-decoration: underline !important;
                          }
                          @keyframes spin {
                            to {
                              transform: rotate(360deg);
                            }
                          }
                          @keyframes ping {
                            75%,
                            100% {
                              transform: scale(2);
                              opacity: 0;
                            }
                          }
                          @keyframes pulse {
                            50% {
                              opacity: 0.5;
                            }
                          }
                          @keyframes bounce {
                            0%,
                            100% {
                              transform: translateY(-25%);
                              animation-timing-function: cubic-bezier(0.8, 0, 1, 1);
                            }
                            50% {
                              transform: none;
                              animation-timing-function: cubic-bezier(0, 0, 0.2, 1);
                            }
                          }
                          @media (max-width: 600px) {
                            .sm-leading-32 {
                              line-height: 32px !important;
                            }
                            .sm-px-24 {
                              padding-left: 24px !important;
                              padding-right: 24px !important;
                            }
                            .sm-py-32 {
                              padding-top: 32px !important;
                              padding-bottom: 32px !important;
                            }
                            .sm-w-full {
                              width: 100% !important;
                            }
                          }
                        </style>
                      </head>
                      <body
                        style="
                          margin: 0;
                          padding: 0;
                          width: 100%;
                          word-break: break-word;
                          -webkit-font-smoothing: antialiased;
                          --bg-opacity: 1;
                          background-color: #ECEFF1;
                          background-color: rgba(236, 239, 241, var(--bg-opacity));
                        "
                      >
                        <div style="display: none">Please verify your email address</div>
                        <div
                          role="article"
                          aria-roledescription="email"
                          aria-label="Verify Email Address"
                          lang="en"
                        >
                          <table
                            style="font-family: Montserrat, -apple-system, &#39;Segoe UI&#39;, sans-serif; width: 100%;"
                            width="100%"
                            cellpadding="0"
                            cellspacing="0"
                            role="presentation"
                          >
                            <tbody>
                              <tr>
                                <td
                                  align="center"
                                  style="--bg-opacity: 1; background-color: #ECEFF1; background-color: rgba(236, 239, 241, var(--bg-opacity)); font-family: Montserrat, -apple-system, &#39;Segoe UI&#39;, sans-serif;"
                                  bgcolor="rgba(236, 239, 241, var(--bg-opacity))"
                                >
                                  <table
                                    class="sm-w-full"
                                    style="font-family: &#39;Montserrat&#39;,Arial,sans-serif; width: 600px;"
                                    width="600"
                                    cellpadding="0"
                                    cellspacing="0"
                                    role="presentation"
                                  >
                                    <tbody>
                                      <tr>
                                        <td
                                          class="sm-py-32 sm-px-24"
                                          style="font-family: Montserrat, -apple-system, &#39;Segoe UI&#39;, sans-serif; padding: 48px; text-align: center;"
                                          align="center"
                                        >
                                            <img
                                              src="file:///C:/Users/Dell/Desktop/PP_UI/PP_TestPlatform_Dev/User%20Interface/Admin%20Interface/src/assets/images/logo/logo-full.svg"
                                              width="155"
                                              alt="Prasad Psycho"
                                              style="
                                                border: 0;
                                                max-width: 100%;
                                                line-height: 100%;
                                                vertical-align: middle;
                                              "
                                            />
                                          </a>
                                        </td>
                                      </tr>
                                      <tr>
                                        <td
                                          align="center"
                                          class="sm-px-24"
                                          style="font-family: &#39;Montserrat&#39;,Arial,sans-serif;"
                                        >
                                          <table
                                            style="font-family: &#39;Montserrat&#39;,Arial,sans-serif; width: 100%;"
                                            width="100%"
                                            cellpadding="0"
                                            cellspacing="0"
                                            role="presentation"
                                          >
                                            <tbody>
                                              <tr>
                                                <td
                                                  class="sm-px-24"
                                                  style="--bg-opacity: 1; background-color: #FFFFFF; background-color: rgba(255, 255, 255, var(--bg-opacity)); border-radius: 4px; font-family: Montserrat, -apple-system, &#39;Segoe UI&#39;, sans-serif; font-size: 14px; line-height: 24px; padding: 48px; text-align: left; --text-opacity: 1; color: #626262; color: rgba(98, 98, 98, var(--text-opacity));"
                                                  bgcolor="rgba(255, 255, 255, var(--bg-opacity))"
                                                  align="left"
                                                >
                                                  <p
                                                    style="
                                                      font-weight: 600;
                                                      font-size: 18px;
                                                      margin-bottom: 0;
                                                    "
                                                  >
                                                    Hey
                                                  </p>
                                                  <p
                                                    style="
                                                      font-weight: 700;
                                                      font-size: 20px;
                                                      margin-top: 0;
                                                      --text-opacity: 1;
                                                      color: #FF5850;
                                                      color: rgba(255, 88, 80, var(--text-opacity));
                                                    "
                                                  >
                                                    John Doe!
                                                  </p>
                                                  <p style="margin: 0 0 24px ;
                                                                text-align: justify">
                                                [[[CUSTOM_EMAIL_CONTENT]]]
                                                  </p>
                                                  <table
                                                    style="font-family: &#39;Montserrat&#39;,Arial,sans-serif; width: 100%;"
                                                    width="100%"
                                                    cellpadding="0"
                                                    cellspacing="0"
                                                    role="presentation"
                                                  >
                                                    <tbody>
                                                      <tr>
                                                        <td
                                                          style="font-family: &#39;Montserrat&#39;,Arial,sans-serif; padding-top: 32px; padding-bottom: 32px;"
                                                        >
                                                          <div
                                                            style="
                                                              --bg-opacity: 1;
                                                              background-color: #ECEFF1;
                                                              background-color: rgba(
                                                                236,
                                                                239,
                                                                241,
                                                                var(--bg-opacity)
                                                              );
                                                              height: 1px;
                                                              line-height: 1px;
                                                            "
                                                          >
                                                            ‌
                                                          </div>
                                                        </td>
                                                      </tr>
                                                    </tbody>
                                                  </table>
                                                  <p style="margin: 0 0 16px">
                                                    Not sure why you received this email? Please
                                                    <a
                                                      href="mailto:support@example.com"
                                                      class="hover-underline"
                                                      style="
                                                        --text-opacity: 1;
                                                        color: #7367F0;
                                                        color: rgba(
                                                          115,
                                                          103,
                                                          240,
                                                          var(--text-opacity)
                                                        );
                                                        text-decoration: none;
                                                      "
                                                      >let us know</a
                                                    >.
                                                  </p>
                                                  <p style="margin: 0 0 16px">
                                                    Thanks, <br />The Prasad Psycho Team
                                                  </p>
                                                </td>
                                              </tr>
                                              <tr>
                                                <td
                                                  style="font-family: &#39;Montserrat&#39;,Arial,sans-serif; height: 20px;"
                                                  height="20"
                                                ></td>
                                              </tr>
                                              <tr>
                                                <td
                                                  style="font-family: &#39;Montserrat&#39;,Arial,sans-serif; height: 16px;"
                                                  height="16"
                                                ></td>
                                              </tr>
                                            </tbody>
                                          </table>
                                        </td>
                                      </tr>
                                    </tbody>
                                  </table>
                                </td>
                              </tr>
                            </tbody>
                          </table>
                        </div>
                      </body>
                    </html>
                            """;

    public static final String WELCOME_TEMPLATE =
            """
                    <!DOCTYPE html>
                    <html
                      lang="en"
                      xmlns:v="urn:schemas-microsoft-com:vml"
                      xmlns:o="urn:schemas-microsoft-com:office:office"
                    >
                      <head>
                        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
                                
                        <meta name="x-apple-disable-message-reformatting" />
                        <meta http-equiv="x-ua-compatible" content="ie=edge" />
                        <meta name="viewport" content="width=device-width, initial-scale=1" />
                        <meta
                          name="format-detection"
                          content="telephone=no, date=no, address=no, email=no"
                        />
                        <!--[if mso]>
                          <xml
                            ><o:OfficeDocumentSettings
                              ><o:PixelsPerInch>96</o:PixelsPerInch></o:OfficeDocumentSettings
                            ></xml
                          >
                          <style>
                            td,
                            th,
                            div,
                            p,
                            a,
                            h1,
                            h2,
                            h3,
                            h4,
                            h5,
                            h6 {
                              font-family: "Segoe UI", sans-serif;
                              mso-line-height-rule: exactly;
                            }
                          </style>
                        <![endif]-->
                        <title>Verify Email Address</title>
                        <link href="./verify._files/css" rel="stylesheet" media="screen" />
                        <style>
                          .hover-underline:hover {
                            text-decoration: underline !important;
                          }
                                
                          @keyframes spin {
                            to {
                              transform: rotate(360deg);
                            }
                          }
                                
                          @keyframes ping {
                            75%,
                            100% {
                              transform: scale(2);
                              opacity: 0;
                            }
                          }
                                
                          @keyframes pulse {
                            50% {
                              opacity: 0.5;
                            }
                          }
                                
                          @keyframes bounce {
                            0%,
                            100% {
                              transform: translateY(-25%);
                              animation-timing-function: cubic-bezier(0.8, 0, 1, 1);
                            }
                                
                            50% {
                              transform: none;
                              animation-timing-function: cubic-bezier(0, 0, 0.2, 1);
                            }
                          }
                                
                          @media (max-width: 600px) {
                            .sm-leading-32 {
                              line-height: 32px !important;
                            }
                                
                            .sm-px-24 {
                              padding-left: 24px !important;
                              padding-right: 24px !important;
                            }
                                
                            .sm-py-32 {
                              padding-top: 32px !important;
                              padding-bottom: 32px !important;
                            }
                                
                            .sm-w-full {
                              width: 100% !important;
                            }
                          }
                        </style>
                      </head>
                                
                      <body
                        style="
                          margin: 0;
                          padding: 0;
                          width: 100%;
                          word-break: break-word;
                          -webkit-font-smoothing: antialiased;
                          --bg-opacity: 1;
                          background-color: #eceff1;
                          background-color: rgba(236, 239, 241, var(--bg-opacity));
                        "
                      >
                        <div style="display: none">Please verify your email address</div>
                        <div
                          role="article"
                          aria-roledescription="email"
                          aria-label="Verify Email Address"
                          lang="en"
                        >
                          <table
                            style="font-family: Montserrat, -apple-system, &#39;Segoe UI&#39;, sans-serif; width: 100%;"
                            width="100%"
                            cellpadding="0"
                            cellspacing="0"
                            role="presentation"
                          >
                            <tbody>
                              <tr>
                                <td
                                  align="center"
                                  style="--bg-opacity: 1; background-color: #eceff1; background-color: rgba(236, 239, 241, var(--bg-opacity)); font-family: Montserrat, -apple-system, &#39;Segoe UI&#39;, sans-serif;"
                                  bgcolor="rgba(236, 239, 241, var(--bg-opacity))"
                                >
                                  <table
                                    class="sm-w-full"
                                    style="font-family: &#39;Montserrat&#39;,Arial,sans-serif; width: 600px;"
                                    width="600"
                                    cellpadding="0"
                                    cellspacing="0"
                                    role="presentation"
                                  >
                                    <tbody>
                                      <tr>
                                        <td
                                          class="sm-py-32 sm-px-24"
                                          style="font-family: Montserrat, -apple-system, &#39;Segoe UI&#39;, sans-serif; padding: 48px; text-align: center;"
                                          align="center"
                                        >
                                       \s
                                            <img
                                              src="file:///C:/Users/Dell/Desktop/PP_UI/PP_TestPlatform_Dev/User%20Interface/Admin%20Interface/src/assets/images/logo/logo-full.svg"
                                              width="155"
                                              alt="Prasad Psycho"
                                              style="
                                                border: 0;
                                                max-width: 100%;
                                                line-height: 100%;
                                                vertical-align: middle;
                                              "
                                            />
                                          </a>
                                        </td>
                                      </tr>
                                      <tr>
                                        <td
                                          align="center"
                                          class="sm-px-24"
                                          style="font-family: &#39;Montserrat&#39;,Arial,sans-serif;"
                                        >
                                          <table
                                            style="font-family: &#39;Montserrat&#39;,Arial,sans-serif; width: 100%;"
                                            width="100%"
                                            cellpadding="0"
                                            cellspacing="0"
                                            role="presentation"
                                          >
                                            <tbody>
                                              <tr>
                                                <td
                                                  class="sm-px-24"
                                                  style="--bg-opacity: 1; background-color: #ffffff; background-color: rgba(255, 255, 255, var(--bg-opacity)); border-radius: 4px; font-family: Montserrat, -apple-system, &#39;Segoe UI&#39;, sans-serif; font-size: 14px; line-height: 24px; padding: 48px; text-align: left; --text-opacity: 1; color: #626262; color: rgba(98, 98, 98, var(--text-opacity));"
                                                  bgcolor="rgba(255, 255, 255, var(--bg-opacity))"
                                                  align="left"
                                                >
                                                  <p
                                                    style="
                                                      font-weight: 600;
                                                      font-size: 18px;
                                                      margin-bottom: 0;
                                                    "
                                                  >
                                                    Hey
                                                  </p>
                                                  <p
                                                    style="
                                                      font-weight: 700;
                                                      font-size: 20px;
                                                      margin-top: 0;
                                                      --text-opacity: 1;
                                                      color: #ff5850;
                                                      color: rgba(255, 88, 80, var(--text-opacity));
                                                    "
                                                  >
                                                    [[[PRE_REGISTER_PARTNER_NAME]]]!
                                                  </p>
                                                  <p
                                                    class="sm-leading-32"
                                                    style="
                                                      font-weight: 600;
                                                      font-size: 20px;
                                                      margin: 0 0 16px;
                                                      --text-opacity: 1;
                                                      color: #263238;
                                                      color: rgba(38, 50, 56, var(--text-opacity));
                                                    "
                                                  >
                                                    Thanks for signing up! 👋
                                                  </p>
                                                  <p style="margin: 0 0 24px">
                                                    Please Proceed  by clicking the
                                                    below button to Register yourself.
                                                 \s
                                                  </p>
                                                  <p style="margin: 0 0 24px">
                                                    If you did not sign up to Prasad Psycho, Please
                                                    ignore this email.\s
                                                  </p>
                                              \s
                                                  <table
                                                    style="font-family: &#39;Montserrat&#39;,Arial,sans-serif;"
                                                    cellpadding="0"
                                                    cellspacing="0"
                                                    role="presentation"
                                                  >
                                                    <tbody>
                                                      <tr>
                                                        <td
                                                          style="mso-padding-alt: 16px 24px; --bg-opacity: 1; background-color: #7367f0; background-color: rgba(115, 103, 240, var(--bg-opacity)); border-radius: 4px; font-family: Montserrat, -apple-system, &#39;Segoe UI&#39;, sans-serif;"
                                                          bgcolor="rgba(115, 103, 240, var(--bg-opacity))"
                                                        >
                                                          <a
                                                            href="http://127.0.0.1:4401/#/pages/register?id=[[[PRE_REGISTER_ID]]]"
                                                            style="
                                                              display: block;
                                                              font-weight: 600;
                                                              font-size: 14px;
                                                              line-height: 100%;
                                                              padding: 16px 24px;
                                                              --text-opacity: 1;
                                                              color: #ffffff;
                                                              color: rgba(
                                                                255,
                                                                255,
                                                                255,
                                                                var(--text-opacity)
                                                              );
                                                              text-decoration: none;
                                                            "
                                                            >Proceed to Register →</a
                                                          >
                                                        </td>
                                                      </tr>
                                                    </tbody>
                                                  </table>
                                                  <table
                                                    style="font-family: &#39;Montserrat&#39;,Arial,sans-serif; width: 100%;"
                                                    width="100%"
                                                    cellpadding="0"
                                                    cellspacing="0"
                                                    role="presentation"
                                                  >
                                                    <tbody>
                                                      <tr>
                                                        <td
                                                          style="font-family: &#39;Montserrat&#39;,Arial,sans-serif; padding-top: 32px; padding-bottom: 32px;"
                                                        >
                                                          <div
                                                            style="
                                                              --bg-opacity: 1;
                                                              background-color: #eceff1;
                                                              background-color: rgba(
                                                                236,
                                                                239,
                                                                241,
                                                                var(--bg-opacity)
                                                              );
                                                              height: 1px;
                                                              line-height: 1px;
                                                            "
                                                          >
                                                            ‌
                                                          </div>
                                                        </td>
                                                      </tr>
                                                    </tbody>
                                                  </table>
                                                  <p style="margin: 0 0 16px">
                                                    Not sure why you received this email? Please
                                                    <a
                                                      href="mailto:support@example.com"
                                                      class="hover-underline"
                                                      style="
                                                        --text-opacity: 1;
                                                        color: #7367f0;
                                                        color: rgba(
                                                          115,
                                                          103,
                                                          240,
                                                          var(--text-opacity)
                                                        );
                                                        text-decoration: none;
                                                      "
                                                      >let us know</a
                                                    >.
                                                  </p>
                                                  <p style="margin: 0 0 16px">
                                                    Thanks, <br />The Prasad Psycho Team
                                                  </p>
                                                </td>
                                              </tr>
                                              <tr>
                                                <td
                                                  style="font-family: &#39;Montserrat&#39;,Arial,sans-serif; height: 20px;"
                                                  height="20"
                                                ></td>
                                              </tr>
                                          \s
                                              <tr>
                                                <td
                                                  style="font-family: &#39;Montserrat&#39;,Arial,sans-serif; height: 16px;"
                                                  height="16"
                                                ></td>
                                              </tr>
                                            </tbody>
                                          </table>
                                        </td>
                                      </tr>
                                    </tbody>
                                  </table>
                                </td>
                              </tr>
                            </tbody>
                          </table>
                        </div>
                      </body>
                    </html>
                            """;
}
