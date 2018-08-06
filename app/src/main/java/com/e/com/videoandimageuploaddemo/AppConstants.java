package com.e.com.videoandimageuploaddemo;

import android.content.Context;
import android.os.Environment;
import android.text.format.DateFormat;
import android.util.Base64;
import android.util.TypedValue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created by SOL13 on 4/7/2016.
 */
public class AppConstants
    {

        //public static String Base_URL = "http://192.168.55.200:7001/";
        //public static String Base_URL = "http://192.168.55.185:7001/";
        // public static String Base_URL = "http://eapps36.e-emphasys.com:1234/eServiceTechServices/";
        //public static String Base_URL = "http://115.111.205.102:1233/";
        //public static String Base_URL = "http://192.168.25.36:10001/eServiceTechServices_2.1/";
        //public static String Base_URL = "http://192.168.25.36:10001/eServiceTechService_2.2/";
        // public static String Base_URL = "http://192.168.25.36:10001/eServiceTechService_2.3/";
        //public static String Base_URL = "http://192.168.25.36:10001/eServiceTechService_2.4/";
        public static String Base_URL = "http://192.168.25.36:10001/eServiceTechService_2.5/";
        //public static String Base_URL = "http://34.237.208.44:9001/eServiceTechService/";
        //public static String Base_URL = "http://eapps36.e-emphasys.com:9099/eServiceTechService/";
        //public static String Base_URL = "http://192.168.25.36:10002/eServiceTechService_2.5/";
        //public static String Base_URL = "http://192.168.55.207:7001/";
        //public static String Base_URL = "http://192.168.55.192:7001/";
        //public static String Base_URL = "http://xappsportal-demo.e-emphasys.com:1800/eServiceTechService_2.1/";
        //public static String Base_URL = "http://e-apps.mlholdings.com:9003/eServiceTechService/";
        //public static String Base_URL = "http://mobile.genequip.com:9001/eServiceTechService/";
        //public static String Base_URL = "http://e-apps.eetcld.com:5050/eServiceTechService/";

        public static String NameSpace = "http://tempuri.org/";
        public static String Authentication_Service = "OrganisationManager/AuthenticationService.svc";
        public static String Setting_Service = "Settings/SettingsService.svc";
        public static String Employee_Service = "OrganisationManager/EmployeeService.svc";
        public static String ServiceOrder_Service = "ServiceManager/ServiceOrderService.svc";
        public static String Task_Service = "ServiceManager/TaskService.svc";
        public static String Part_Service = "ServiceManager/PartService.svc";
        public static String ServiceText_Service = "ServiceText/ServiceTextService.svc";
        public static String Equipment_Service = "ServiceManager/EquipmentService.svc";
        public static String Report_Service = "ReportManager/ReportService.svc";
        public static String Synchronize_Service = "ServiceManager/Synchronize.svc";
        public static String Labor_Service = "ServiceManager/LaborTimeRecordsService.svc";
        public static String Other_Service = "ServiceManager/OthersService.svc";
        public static String Customer_Service = "OrganisationManager/CustomerService.svc";

        public static String FONTNAME_HELVETICA_35_THIN = "HelveticaNeueLTPro-Th.otf";
        public static String FONTNAME_HELVETICA_NEUE_55_ROMAN = "helvetica_neue_lt_com_55_roman.ttf";
        public static String FONTNAME_HELVETICA_NEUE_LT_STD_65_MEDIUM = "helveticaneueltstd-md-webfont.ttf";
        public static String FONTNAME_HELVETICA_45_LIGHT = "HelveticaNeueLTStd-Lt.otf";
        public static String FONTNAME_HELVETICA_CONEDENSED_BLACK = "HelveticaCdBd_0.ttf";
        public static String COMMON_ERROR_MESSAGE = "Invalid Call Request.";

        public static int FONT_SIZE_8 = 8;
        public static int FONT_SIZE_10 = 10;
        public static int FONT_SIZE_11 = 11;
        public static int FONT_SIZE_12 = 12;
        public static int FONT_SIZE_13 = 13;
        public static int FONT_SIZE_14 = 14;
        public static int FONT_SIZE_15 = 15;
        public static int FONT_SIZE_16 = 16;
        public static int FONT_SIZE_17 = 17;
        public static int FONT_SIZE_18 = 18;
        public static int FONT_SIZE_19 = 19;
        public static int FONT_SIZE_20 = 20;
        public static int FONT_SIZE_22 = 22;
        public static int FONT_SIZE_24 = 24;
        public static int FONT_SIZE_26 = 26;

        public static int ListPageSize = 20;
        public static int AutoSuggestRecords = 10;

        public static String Device_Type = "android";

        public static String DisplayDateFormat = "dd-MMM-yyyy";
        public static String DisplayTimeFormat = "HH:mm";
        public static String ManageLaborTimeFormat = "hh:mm a";
        public static String ServiceDateFormat = "yyyy-MM-dd'T'HH:mm:ss";
        public static String ServiceFullDateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS";
        public static String DefaultDateString = "1970-01-02T00:00:00";
        public static String ManageLaborDateTimeFormat = "dd-MMM-yyyy | HH:mm";

        public static String GeneralSegmentID = "-1";
        public static String LunchSegmentID = "-2";
        public static String ClockInOutSegmentID = "-3";
        public static String IdleSegmentID = "-4";
        public static String TravelSegmentID = "-5";
        public static String TaskStatusCodeForOffline = "-100";
        public static String ClockInOrder = "CLOCKIN";
        public static String ClockInSegmentNo = "100";
        public static String MealBreakOrder = "LunchOrder";
        public static String MealBreakSegmentNo = "100";
        public static String GeneralTask = "GenTask";
        public static String MealTask = "Meal";

        public static String LoginCaller = "login";
        public static String AssignedOrdersCaller = "assignedorders";
        public static String OrderDetailsCaller = "orderdetails";
        public static String SettingsCaller = "settings";
        public static String LogoutCaller = "logout";
        public static String BaseActivityCaller = "baseactivity";
        public static String SplashScreenCaller = "splashscreen";
        public static String StartedTaskCaller = "startedtask";
        public static String PushNotificationCaller = "pushnotification";
        public static String PushNotificationListCaller = "pushnotificationlist";
        public static String AllOrderListCaller = "allorders";

        //public static String InternalPath = "eServiceTech";
        public static String InternalSignOffDirectory = ".eServiceTech/{SO}/{SOS}/Sign Off Reports";
        public static String InternalImageDirectory = ".eServiceTech/{SO}/{SOS}/Images";
        public static String InternalTempImageDirectory = ".eServiceTech/Temp/{SO}/{SOS}/Images";
        public static String InternalDeleteTempImageDirectory = ".eServiceTech/Temp";
        public static String InternalTravelDirectory = ".eServiceTech/Temp/{SO}/{SOS}/Travel";
        public static String LogsDirectory = Environment.getExternalStorageDirectory() + "/.eServiceTech/Logs";

        public static String SendForApproval = "true";

        public static int VideoFileChunkSize = 1500000;

        //static LocationService objLocationService = null;
        public static Context context = null;

        public enum TimeLogStatus
            {
                WIP,
                APPROVED
            }

        public enum AutoSuggestTypes
            {
                PARTS,
                TECHSMANAGELABOR,
                PLACES,
                OTHERS,
                SERVICE_QUOTE_REQUEST_EMPLOYEE_DETAILS,
                SERVICE_QUOTE_REQUEST_CUSTOMER_DETAILS,
                SERVICE_QUOTE_REQUEST_EQUIPMENT_DETAILS,
            }

        public enum EmployeeAction
            {
                Unactioned,
                Accepted,
                Rejected,
                Started,
                Stopped,
                Completed,
                Released,
                TravelStarted,
                TravelStopped
            }

        public enum ServiceOrderStatus
            {
                Complete,
                Realesed
            }

        public enum FileTypes
            {
                Images,
                Videos,
                Others,
                SignOff,
                Travel
            }

        public enum AttachmentStatus
            {
                DownloadPending,
                Downloaded,
                UploadPending,
                Uploading,
                Uploaded
            }

        public static String EncodeString(String data)
            {
                String result = Base64.encodeToString(data.getBytes(), Base64.DEFAULT);
                return result;
            }

        public static String DecodeString(String data)
            {
                String result = new String(Base64.decode(data, Base64.DEFAULT));
                return result;
            }

        public static String FormatDateTime(Date datetime)
            {
                return DateFormat.format("yyyy-MM-ddTHH:mm:ss", datetime).toString();
            }

        public static String FormatDateTimeWTSec(Date datetime)
            {
                return DateFormat.format("yyyy-MM-ddTHH:mm:00", datetime).toString();
            }

        public static String FormatDateWTTime(Date datetime)
            {
                return DateFormat.format("yyyy-MM-ddT00:00:00", datetime).toString();
            }

        public static String FormatDateTime(Date datetime, String format)
            {
                return DateFormat.format(format, datetime).toString();
            }

        public static String FormatDateTimeVariable(Date datetime, String format)
            {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                String result = sdf.format(datetime);
                return result;
            }


        public static Date StringToDateTime(String dateTime, String format)
            {
                Date outputDate = null;
                try
                    {
                        if (dateTime != null && dateTime != "")
                            {
                                SimpleDateFormat df_input = new SimpleDateFormat(format, Locale.ENGLISH);
                                outputDate = df_input.parse(dateTime);
                            }
                    } catch (ParseException e)
                    {

                    }
                return outputDate;
            }

        public static Date GetDefaultDate()
            {
                return StringDateToDate(DefaultDateString, ServiceDateFormat);
            }

        public static boolean isDefaultDate(String dateTime, String format)
            {
                Date value = StringToDateTime(dateTime, format);
                return !value.after(GetDefaultDate());
            }

        public static String FormatDateTimeDisplay(String Datetime, String inputFormat, String outputFormat)
            {
                Date parsed = null;
                String outputDate = "";
                try
                    {
                        if (Datetime != null && Datetime != "")
                            {
                                SimpleDateFormat df_input = new SimpleDateFormat(inputFormat, Locale.ENGLISH);
                                SimpleDateFormat df_output = new SimpleDateFormat(outputFormat, Locale.ENGLISH);
                                parsed = df_input.parse(Datetime);
                                outputDate = df_output.format(parsed);
                            }
                        else
                            outputDate = ParseNullEmptyString(outputDate);
                        String defaultDate = "01-Jan-1970";
                        if (outputDate.equals(defaultDate))
                            outputDate = ParseNullEmptyString("");
                    } catch (ParseException e)
                    {
                        e.printStackTrace();

                    }
                return outputDate;
            }

        public static String ParseNullEmptyString(String value)
            {
                String result = value;
                if (value == null || value.trim().equals(""))
                    result = "N/A";
                return result.trim().replaceAll(" +", " ");
            }

        public static String ParseNullEmptyNumeric(String value)
            {
                String result = value;
                if (value == null || value == "")
                    result = "0";
                return result.trim().replaceAll(" +", " ");
            }

//        public static String ArrayListToJSONString(ArrayList<Map<Object, Object>> object)
//            {
//                String result;
//
//               // result = new Gson().toJson(object);
//
//                return result;
//            }

//        public static String MapToJSONString(Map<Object, Object> object)
//            {
//                String result = "";
//                try
//                    {
//                        result = new Gson().toJson(object);
//                    } catch (Exception ex)
//                    {
//                        ex.printStackTrace();
//                    }
//                return result;
//            }

//        public static ArrayList<Map<Object, Object>> JSONStringToArrayList(String JSONString)
//            {
//                ArrayList<Map<Object, Object>> result;
//
//                result = (ArrayList<Map<Object, Object>>) new Gson().fromJson(JSONString, new TypeToken<ArrayList<Map<Object, Object>>>()
//                    {
//                    }.getType());
//
//                return result;
//            }

//        public static Map<Object, Object> JSONStringToMap(String JSONString)
//            {
//                Map<Object, Object> result;
//
//                result = (Map<Object, Object>) new Gson().fromJson(JSONString, new TypeToken<Map<Object, Object>>()
//                    {
//                    }.getType());
//
//                return result;
//            }

//        public static String GetCurrentTimeInLocalTimezone()
//            {
//                String result = null;
//                try
//                    {
//                        SimpleDateFormat sdf = new SimpleDateFormat(ServiceDateFormat);
//                        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
//                        Calendar calendar = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
//                        calendar.setTime(new Date());
//                        if (SessionHelper.LoggedInEmployee.EmployeeData.get("TimeZoneOffset") != null)
//                            calendar.add(Calendar.SECOND, Integer.valueOf(SessionHelper.LoggedInEmployee.EmployeeData.get("TimeZoneOffset").toString()));
//                        result = sdf.format(calendar.getTime());
//                        //result = sdf.parse(utcTime);
//                    } catch (Exception e)
//                    {
//                        e.printStackTrace();
//                    }
//
//                return result;
//            }


        public static Date GetCurrentUTCTime()
            {
                //note: doesn't check for null
                return StringDateToDate(GetUTCdatetimeAsString());
            }

        public static String GetUTCdatetimeAsString()
            {
                final SimpleDateFormat sdf = new SimpleDateFormat(ServiceFullDateFormat);
                sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
                final String utcTime = sdf.format(new Date());

                return utcTime;
            }

        public static Date StringDateToDate(String StrDate)
            {
                Date dateToReturn = null;
                SimpleDateFormat dateFormat = new SimpleDateFormat(ServiceFullDateFormat);

                try
                    {
                        dateToReturn = (Date) dateFormat.parse(StrDate);
                    } catch (ParseException e)
                    {
                        e.printStackTrace();
                    }

                return dateToReturn;
            }

        public static Date StringDateToDate(String StrDate, String format)
            {
                Date dateToReturn = null;
                SimpleDateFormat dateFormat = new SimpleDateFormat(format);

                try
                    {
                        dateToReturn = (Date) dateFormat.parse(StrDate);
                    } catch (ParseException e)
                    {
                        e.printStackTrace();
                    }

                return dateToReturn;
            }

//        public static Date GetEmployeeUTCDateTime()
//            {
//                Date result = null;
//                try
//                    {
//                        SimpleDateFormat sdf = new SimpleDateFormat(ServiceDateFormat);
//                        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
//                        String currentDateandTime = sdf.format(new Date());
//                        Date date = sdf.parse(currentDateandTime);
//                        Calendar calendar = Calendar.getInstance();
//                        calendar.setTime(date);
//                        if (SessionHelper.LoggedInEmployee.EmployeeData.get("TimeZoneOffset") != null)
//                            calendar.add(Calendar.SECOND, Integer.valueOf(SessionHelper.LoggedInEmployee.EmployeeData.get("TimeZoneOffset").toString()));
//                        result = calendar.getTime();
//                    } catch (ParseException e)
//                    {
//                        e.printStackTrace();
//                    }
//
//                return result;
//            }
//
//        public static Date GetEmployeeUTCDate()
//            {
//                Date result = null;
//                try
//                    {
//                        SimpleDateFormat sdf = new SimpleDateFormat(ServiceDateFormat);
//                        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
//                        String currentDateandTime = sdf.format(new Date());
//                        Date date = sdf.parse(currentDateandTime);
//                        Calendar calendar = Calendar.getInstance();
//                        calendar.setTime(date);
//                        if (SessionHelper.LoggedInEmployee.EmployeeData.get("TimeZoneOffset") != null)
//                            calendar.add(Calendar.SECOND, Integer.valueOf(SessionHelper.LoggedInEmployee.EmployeeData.get("TimeZoneOffset").toString()));
//                        result = removeTime(calendar.getTime());
//                    } catch (ParseException e)
//                    {
//                        e.printStackTrace();
//                    }
//
//                return result;
//            }
//
//        public static Date GetEmployeeCurrentDateTime()
//            {
//                Date result = null;
//                try
//                    {
//           /* SimpleDateFormat sdf = new SimpleDateFormat(ServiceDateFormat);
//            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
//            String currentDateandTime = sdf.format(new Date());
//            Date date = sdf.parse(currentDateandTime);*/
//                        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
//                        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
//                        /*calendar.setTime(date);*/
//                        if (SessionHelper.LoggedInEmployee.EmployeeData.get("TimeZoneOffset") != null)
//                            calendar.add(Calendar.SECOND, Integer.valueOf(SessionHelper.LoggedInEmployee.EmployeeData.get("TimeZoneOffset").toString()));
//                        result = calendar.getTime();
//                    } catch (Exception e)
//                    {
//                        e.printStackTrace();
//                    }
//
//                return result;
//            }
//
//        public static Date GetEmployeeCurrentDate()
//            {
//                Date result = null;
//                try
//                    {
//                        result = GetEmployeeCurrentDateTime();
//                        Calendar cal = Calendar.getInstance();
//                        cal.setTime(result);
//                        cal.set(Calendar.HOUR_OF_DAY, 0);
//                        cal.set(Calendar.MINUTE, 0);
//                        cal.set(Calendar.SECOND, 0);
//                        cal.set(Calendar.MILLISECOND, 0);
//                        result = cal.getTime();
//                    } catch (Exception e)
//                    {
//                        e.printStackTrace();
//                    }
//
//                return result;
//            }

        public static Date RemoveSecAndMilliSecFromDate(Date objDate)
            {
                Date result = null;
                try
                    {
                        result = objDate;
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(result);
                        cal.set(Calendar.SECOND, 0);
                        cal.set(Calendar.MILLISECOND, 0);
                        result = cal.getTime();
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                return result;
            }

        public static Date Set1SecInTime(Date objDate)
            {
                Date result = null;
                try
                    {
                        result = objDate;
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(result);
                        cal.set(Calendar.SECOND, 1);
                        cal.set(Calendar.MILLISECOND, 0);
                        result = cal.getTime();
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                return result;
            }

//        public static Date GetEmployeeLocalTime(Date utcTime)
//            {
//                Date result = null;
//                try
//                    {
//                        SimpleDateFormat sdf = new SimpleDateFormat(ServiceDateFormat);
//                        Calendar calendar = Calendar.getInstance();
//                        calendar.setTime(utcTime);
//                        if (SessionHelper.LoggedInEmployee.EmployeeData.get("TimeZoneOffset") != null)
//                            calendar.add(Calendar.SECOND, Integer.valueOf(SessionHelper.LoggedInEmployee.EmployeeData.get("TimeZoneOffset").toString()));
//                        result = calendar.getTime();
//                    } catch (Exception e)
//                    {
//                        e.printStackTrace();
//                    }
//
//                return result;
//            }

//        public static Date GetTimeInUTC(Date utcTime)
//            {
//                Date result = null;
//                try
//                    {
//                        Calendar calendar = Calendar.getInstance();
//                        calendar.setTime(utcTime);
//                        if (SessionHelper.LoggedInEmployee.EmployeeData.get("TimeZoneOffset") != null)
//                            calendar.add(Calendar.SECOND, -1 * Integer.valueOf(SessionHelper.LoggedInEmployee.EmployeeData.get("TimeZoneOffset").toString()));
//                        result = calendar.getTime();
//                    } catch (Exception e)
//                    {
//                        e.printStackTrace();
//                    }
//
//                return result;
//            }

        public static Date GetDefaultExtendDate()
            {
                return GetDateObject(1970, 1, 1);
            }

        public static Date GetDateObject(int year, int month, int day)
            {
                Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, month - 1);
                cal.set(Calendar.DAY_OF_MONTH, day);
                cal.set(Calendar.HOUR_OF_DAY, 0);
                cal.set(Calendar.MINUTE, 0);
                cal.set(Calendar.SECOND, 0);
                cal.set(Calendar.MILLISECOND, 0);
                return cal.getTime();
            }

        public static Date GetMaxTimeOfDate(Date date, int hour, int minute, int second)
            {
                Calendar cal = new GregorianCalendar();
                cal.setTime(date);
                cal.set(Calendar.HOUR, hour);
                cal.set(Calendar.MINUTE, minute);
                cal.set(Calendar.SECOND, second);
                cal.set(Calendar.MILLISECOND, 0);
                return cal.getTime();
            }

        public static Date removeTime(Date date)
            {
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                cal.set(Calendar.HOUR_OF_DAY, 0);
                cal.set(Calendar.MINUTE, 0);
                cal.set(Calendar.SECOND, 0);
                cal.set(Calendar.MILLISECOND, 0);
                return cal.getTime();
            }

        public static Map<Object, Object> CreateOrder(String SONo, String SOSNo, String estStartTime, String estEndTime,
                                                      String ServiceCenter, String company)
            {
                Map<Object, Object> result = null;
                try
                    {
                        result = new LinkedHashMap<>();
                        result.put("ServiceOrderNo", SONo);
                        result.put("ServiceOrderSegmentNo", SOSNo);
                        result.put("EstimatedStartTime", estStartTime);
                        result.put("EstimatedEndTime", estEndTime);
                        result.put("ServiceCenter", ServiceCenter);
                        result.put("TaskStartedServiceCenter", ServiceCenter);
                        result.put("Company", company);

                    } catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }

                return result;
            }

        public static Map<Object, Object> CreateTask(String SONo, String SOSNo, String segmentID, String taskCode,
                                                     String estStartTime, String estEndTime, String serviceCenter, String description)
            {
                Map<Object, Object> result = null;
                try
                    {
                        result = new LinkedHashMap<>();
                        result.put("SONumber", SONo);
                        result.put("SOSNumber", SOSNo);
                        result.put("SegmentID", segmentID);
                        result.put("TaskCode", taskCode);
                        result.put("EstimatedStartTime", estStartTime);
                        result.put("EstimatedEndTime", estEndTime);
                        result.put("TaskStartedServiceCenter", serviceCenter);
                        result.put("Description", description);

                    } catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }

                return result;
            }

//        public static Map<Object, Object> CreateLunchOrder()
//            {
//                Map<Object, Object> result = null;
//                try
//                    {
//                        result = CreateOrder(MealBreakOrder, MealBreakSegmentNo, GetCurrentTimeInLocalTimezone(),
//                                FormatDateTime(new Date(StringToDateTime(GetCurrentTimeInLocalTimezone(), ServiceDateFormat).getTime() + 1800000)),
//                                SessionHelper.getCredentials().getServiceCenterKey(), SessionHelper.getCredentials().getCompany());
//                    } catch (Exception ex)
//                    {
//                        ex.printStackTrace();
//                    }
//
//                return result;
//            }

//        public static Map<Object, Object> CreateLunchTask(Map<Object, Object> lunchOrder)
//            {
//                Map<Object, Object> result = null;
//                try
//                    {
//                        result = CreateTask(lunchOrder.get("ServiceOrderNo").toString(),
//                                lunchOrder.get("ServiceOrderSegmentNo").toString(), LunchSegmentID, MealTask,
//                                lunchOrder.get("EstimatedStartTime").toString(), lunchOrder.get("EstimatedEndTime").toString(),
//                                SessionHelper.getCredentials().getServiceCenterKey(), "Meal Break");
//                    } catch (Exception ex)
//                    {
//                        ex.printStackTrace();
//                    }
//
//                return result;
//            }

        public static Map<Object, Object> CreateGeneralTask(Map<Object, Object> generalOrder)
            {
                Map<Object, Object> result = null;
                try
                    {
                        result = CreateTask(generalOrder.get("ServiceOrderNo").toString(),
                                generalOrder.get("ServiceOrderSegmentNo").toString(), GeneralSegmentID, GeneralTask,
                                generalOrder.get("EstimatedStartTime").toString(),
                                generalOrder.get("EstimatedEndTime").toString(),
                                generalOrder.get("ServiceCenter").toString(), "General Task");
                    } catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }

                return result;
            }

        public static double GetDateDifference(Date startTime, Date endTime)
            {
                final int MILLI_TO_HOUR = 1000 * 60 * 60;
                long start = startTime.getTime();
                long end = endTime.getTime();

                long diff = end - start;

                return round((1.0 * diff) / (1.0 * MILLI_TO_HOUR), 2);
            }

        public static double round(double value, int places)
            {
                if (places < 0) throw new IllegalArgumentException();

                BigDecimal bd = new BigDecimal(value);
                bd = bd.setScale(places, RoundingMode.HALF_UP);
                return bd.doubleValue();
            }

//        public static Map<Object, Object> GetAccessRightsDetails(String featurename)
//            {
//                Map<Object, Object> result = null;
//                if (SessionHelper.LoggedInEmployee != null && SessionHelper.LoggedInEmployee.EmployeeData != null)
//                    {
//                        ArrayList<Map<Object, Object>> accessrights = null;
//                        try
//                            {
//                                accessrights = (ArrayList) ((Map) SessionHelper.LoggedInEmployee.EmployeeData.get("AccessRights")).get("AccessRights");
//                                if (accessrights != null && accessrights.size() > 0)
//                                    {
//                                        for (Map<Object, Object> access : accessrights)
//                                            {
//                                                if (access.get("MenuCode").toString().equals(featurename))
//                                                    {
//                                                        result = access;
//                                                        break;
//                                                    }
//                                            }
//                                    }
//
//                            } catch (Exception ex)
//                            {
//                                ex.printStackTrace();
//                            }
//                    }
//                return result;
//            }

        public static boolean IsAssignedOrder(String estimatedStartTime)
            {
                boolean result = false;

                try
                    {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        Date strDate = sdf.parse("03/01/1970");
                        if (StringToDateTime(estimatedStartTime, ServiceDateFormat).after(strDate))
                            result = true;
                        else
                            result = false;
                    } catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
                return result;
            }

        public static boolean IsAssignedOrder(Date estimatedStartTime)
            {
                boolean result = false;

                try
                    {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        Date strDate = sdf.parse("03/01/1970");
                        if (estimatedStartTime.after(strDate))
                            result = true;
                        else
                            result = false;
                    } catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
                return result;
            }

//        public static void CleanAllData(Context context)
//            {
//                CalendarEvents ce = new CalendarEvents(context);
//                ce.DeleteAllCalendarEvents();
//                CDHelper.deleteAll();
//                deleteRecursive(new File(Environment.getExternalStorageDirectory()
//                        .toString() + "/eServiceTech"));
//            }

        public static void deleteRecursive(File fileOrDirectory)
            {
                if (fileOrDirectory.isDirectory())
                    for (File child : fileOrDirectory.listFiles())
                        deleteRecursive(child);

                fileOrDirectory.delete();
            }

        public static int DptoPix(Context context, int sizeinDP)
            {
                return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, sizeinDP, context.getResources().getDisplayMetrics());
            }

        public static String ValidFileName(String fileName)
            {
                String result = fileName;
                char[] invalidChars = new char[]{'\\', '/', ':', '*', '?', '\"', '<', '>', '|', '\'', '#', '@'};
                for (char ch : invalidChars)
                    result = result.replace(ch, '_');
                return result;
            }

        public static void CopyFile(File sourceFile, File destinationFile)
            {
                try
                    {
                        InputStream is = new FileInputStream(sourceFile); //get file location from internal
                        OutputStream os = new FileOutputStream(destinationFile); //Open your OutputStream and pass in the file you want to write to
                        byte[] toWrite = new byte[is.available()]; //Init a byte array for handing data transfer
                        // Log.i("Available ", is.available() + "");
                        int result = is.read(toWrite); //Read the data from the byte array
                        // Log.i("Result", result + "");
                        os.write(toWrite); //Write it to the output stream
                        is.close(); //Close it
                        os.close(); //Close it
           /* UIHelper.showAlertDialog(this, getResources().getString(R.string.success),
                    getResources().getString(R.string.signoffdownloadsuccess), getResources().getString(R.string.ok), null);*/
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
            }

        public static String GetLassoFileName(String fileName)
            {
                String result = fileName;
                result = result.substring(0, result.lastIndexOf(".")) + "_Lasso" + result.substring(result.lastIndexOf("."), result.length());
                return result;
            }

        public static String GetLassoImageFilePath(String fileName, String SONo, String SOSNo)
            {
                String lassoFileName = GetLassoFileName(fileName);
                String lassofilePath = Environment.getExternalStorageDirectory()
                        .toString() + "/" + AppConstants.InternalImageDirectory.replace("{SO}", SONo)
                        .replace("{SOS}", SOSNo)
                        + "/" + lassoFileName;

                return lassofilePath;
            }

        public static String GetImageFilePath(String fileName, String SONo, String SOSNo)
            {
                String folderPath = Environment.getExternalStorageDirectory()
                        .toString() + "/" + AppConstants.InternalImageDirectory.replace("{SO}", SONo)
                        .replace("{SOS}", SOSNo);
                File file = new File(folderPath);
                if (!file.exists())
                    file.mkdirs();
                String filePath = folderPath + "/" + fileName;

                return filePath;
            }

        public static String GetTravelFilePath(String fileName, String SONo, String SOSNo)
            {
                String folderPath = Environment.getExternalStorageDirectory()
                        .toString() + "/" + AppConstants.InternalTravelDirectory.replace("{SO}", SONo)
                        .replace("{SOS}", SOSNo);
                File file = new File(folderPath);
                if (!file.exists())
                    file.mkdirs();
                String filePath = folderPath + "/" + fileName;

                return filePath;
            }

        public static Date AddSecondsToDate(Date datetime, int seconds)
            {
                Date mDate = new Date(datetime.getTime() + seconds * 1000);
                return mDate;
            }

        public static void DeleteTempImageFolder()
            {
                String tempFolder = Environment.getExternalStorageDirectory().toString() + "/" +
                        AppConstants.InternalDeleteTempImageDirectory;
                File temp = new File(tempFolder);
                if (temp.exists())
                    deleteRecursive(temp);
            }

        public static void DeleteMainFileFolder()
            {
                String tempFolder = Environment.getExternalStorageDirectory().toString() + "/" +
                        ".eServiceTech";
                File temp = new File(tempFolder);
                if (temp.exists())
                    deleteRecursive(temp);
            }
//
//        public static String GetIdleTimeServiceCenter()
//            {
//                String serviceCenter = "";
//                String mealIdleServiceCenter = SessionHelper.currentSettings.Settings.get("MealIdleServiceCenter").toString();
//                if (mealIdleServiceCenter.equals("1"))
//                    serviceCenter = SessionHelper.LoggedInEmployee.EmployeeData.get("ClockInServiceCenter").toString();
//                else
//                    {
//                        boolean found = false;
//                        Map<Object, Object> mappedCompaniesServiceCenters = (Map<Object, Object>) SessionHelper.LoggedInEmployee.EmployeeData.get("MappedCompaniesServiceCenters");
//                        ArrayList companyservicecenter = (ArrayList) mappedCompaniesServiceCenters.get("CompanyServiceCenterPair");
//                        for (int i = 0; i < companyservicecenter.size(); i++)
//                            {
//                                if (((Map<Object, Object>) companyservicecenter.get(i)).get("Company").equals(SessionHelper.getCredentials().getCompany()))
//                                    {
//                                        serviceCenter = ((Map<Object, Object>) companyservicecenter.get(i)).get("ExtendServiceCenter").toString();
//                                        found = true;
//                                        break;
//                                    }
//                            }
//                        if (!found)
//                            serviceCenter = SessionHelper.LoggedInEmployee.EmployeeData.get("ExtendServiceCenter").toString();
//                    }
//                return serviceCenter;
//            }

        public static final Map<Object, Object> shallowCopy(final Map<Object, Object> source) throws Exception
            {
                final Map<Object, Object> newMap = source.getClass().newInstance();
                newMap.putAll(source);
                return newMap;
            }

        public static String DurationToHoursMins(Double timeDuration)
            {
                //String strduration = String.valueOf(timeDuration);
                String result = "";
      /*  String[] duration = null;
        if (strduration.contains(".")) {
            duration = strduration.split("\\.");

        } else {
            duration = new String[2];
            duration[0] = strduration.toString();
            duration[1] = "0";
        }
        Double minPart = Double.valueOf(Integer.valueOf(duration[1]) * 60 / 10);*/

                // Double duration = Double.valueOf(rowItem.get("Duration").toString());
                int hours = Double.valueOf(timeDuration).intValue();
                int minutes = (int) Math.round((timeDuration - hours) * 60);

                result = String.format("%02d", hours) + " h " + String.format("%02d", minutes) + " m";
                return result;
            }

//        public static void UpdateServiceOrderPartCount(String serviceOrderNo, String segmentNo, Integer partCount)
//            {
//                if (SessionHelper.AssginedOrders != null && SessionHelper.AssginedOrders.size() > 0)
//                    {
//                        for (Map<Object, Object> serviceOrder : SessionHelper.AssginedOrders)
//                            {
//                                if (serviceOrder.get("ServiceOrderNo").toString().equals(serviceOrderNo) &&
//                                        serviceOrder.get("ServiceOrderSegmentNo").toString().equals(segmentNo))
//                                    {
//                                        serviceOrder.put("PartCount", partCount);
//                                    }
//                            }
//                    }
//                CDHelper.UpdatePartCount(serviceOrderNo, segmentNo, partCount);
//            }

//        public static void UpdateServiceOrderOtherCount(String serviceOrderNo, String segmentNo, Integer otherCount)
//            {
//                if (SessionHelper.AssginedOrders != null && SessionHelper.AssginedOrders.size() > 0)
//                    {
//                        for (Map<Object, Object> serviceOrder : SessionHelper.AssginedOrders)
//                            {
//                                if (serviceOrder.get("ServiceOrderNo").toString().equals(serviceOrderNo) &&
//                                        serviceOrder.get("ServiceOrderSegmentNo").toString().equals(segmentNo))
//                                    {
//                                        serviceOrder.put("OthersCount", otherCount);
//                                    }
//                            }
//                    }
//                CDHelper.UpdateOtherCount(serviceOrderNo, segmentNo, otherCount);
//            }
//
//        public static void StartLocationService()
//            {
//                Map<Object, Object> capturedevicelocation = AppConstants.GetAccessRightsDetails("capturedevicelocation");
//                if (SessionHelper.isClockedIn && Boolean.valueOf(capturedevicelocation.get("Authorize").toString()))
//                    {
//                        objLocationService = new LocationService(context);
//                    }
//            }
//
//        public static void StopLocationService()
//            {
//                if (objLocationService != null)
//                    objLocationService.StopService();
//            }

        public static Map<Object, Object> CreateTravelInfo(double srcLat, double srcLong, double destLat, double destLong,
                                                           double estMiles, double estHours, String SO, String SOS,
                                                           String estStartTime, String estEndTime)
            {
                Map<Object, Object> result = null;
                result = new HashMap<>();

                result.put("SourceLatitude", srcLat);
                result.put("SourceLongitude", srcLong);
                result.put("DestinationLatitude", destLat);
                result.put("DestinationLongitude", destLong);
                result.put("EstimatedMiles", estMiles);
                result.put("EstimatedHours", estHours);
                result.put("TravelSONumber", SO);
                result.put("TravelSOSNumber", SOS);
                result.put("TravelEstimatedStartTime", estStartTime);
                result.put("TravelEstimatedEndTime", estEndTime);

                return result;
            }

        public static Map<Object, Object> CreateMapInfo(String company, String dateUploaded, String employeeName,
                                                        String employeeNo, String fileData, String fileName, String SO,
                                                        String SOS, String unitNo)
            {
                Map<Object, Object> result = new HashMap<>();
                result.put("ChunkName", "");
                result.put("Company", company);
                result.put("DateUploaded", dateUploaded);
                result.put("EmployeeName", employeeName);
                result.put("EmployeeNo", employeeNo);
                result.put("FileData", fileData);
                result.put("FileName", fileName);
                result.put("FileType", FileTypes.Travel.toString());
                result.put("FileUrl", "");
                result.put("OriginalData", "");
                result.put("OriginalUrl", "");
                result.put("ServiceOrderNumber", SO);
                result.put("ServiceOrderSegment", SOS);
                result.put("ThumbnailData", fileData);
                result.put("ThumbnailUrl", "");
                result.put("Title", "");
                result.put("UnitNo", unitNo);
                result.put("chunkData", "");
                result.put("objAnnotationDetails", "");

                return result;
            }

        public static Map<Object, Object> CreateServiceOrder(String company, String dateUploaded, String employeeName,
                                                             String employeeNo, String fileData, String fileName, String SO,
                                                             String SOS, String unitNo)
            {
                Map<Object, Object> result = new HashMap<>();
                result.put("ServiceOrderNo", "");
                result.put("ServiceOrderSegmentNo", company);
                result.put("EstimatedStartTime", dateUploaded);
                result.put("EstimatedEndTime", employeeName);
                result.put("EstStartTime", employeeNo);
                result.put("EstEndTime", fileData);
                result.put("PromiseDate", fileName);
                result.put("AssignedDuration", FileTypes.Travel.toString());
                result.put("ServiceCenter", unitNo);
                result.put("ServiceOrderStatus", "");
                result.put("IsPending", "");
                result.put("IsSegmentStarted", "");
                result.put("IsTravelStarted", SO);
                result.put("TaskStartedServiceCenter", SOS);
                result.put("Company", fileData);
                result.put("Action", "");
                result.put("EstimatedMiles", "");

                return result;
            }

        public static Map<Object, Object> CreateTask(String company, String dateUploaded, String employeeName,
                                                     String employeeNo, String fileData, String fileName, String SO,
                                                     String SOS, String unitNo)
            {
                Map<Object, Object> result = new HashMap<>();
                result.put("SONumber", "");
                result.put("SOSNumber", company);
                result.put("SegmentID", dateUploaded);
                result.put("EstimatedMiles", employeeName);
                result.put("OtherLineNO", employeeNo);

                return result;
            }
    }
