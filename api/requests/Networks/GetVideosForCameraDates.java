package com.immediasemi.blink.api.requests.Networks;

import com.immediasemi.blink.api.requests.BlinkRequest;
import com.immediasemi.blink.models.Videos;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class GetVideosForCameraDates
  extends BlinkRequest
{
  private static final String path = "/network/:network/camera/:camera/videos";
  private static final long serialVersionUID = -5643301947627409906L;
  private Integer mDateRange;
  private Integer mDurationHours;
  private Integer mHour;
  
  public GetVideosForCameraDates() {}
  
  public GetVideosForCameraDates(int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramInt1 == 2) {
      this.mHour = Integer.valueOf(0);
    }
    for (this.mDurationHours = Integer.valueOf(24);; this.mDurationHours = Integer.valueOf(paramInt3))
    {
      this.mDateRange = Integer.valueOf(paramInt1);
      return;
      this.mHour = Integer.valueOf(paramInt2);
    }
  }
  
  private String getDateRange()
  {
    String str;
    switch (this.mDateRange.intValue())
    {
    default: 
      str = "past";
    }
    for (;;)
    {
      return str;
      str = "";
      continue;
      str = "yest";
    }
  }
  
  private String getEndDate()
  {
    String str;
    switch (this.mDateRange.intValue())
    {
    default: 
      str = getEndOfDay(-2);
    }
    for (;;)
    {
      return str;
      str = getEndOfDay(0);
      continue;
      str = getEndOfDay(-1);
    }
  }
  
  private String getEndOfDay(int paramInt)
  {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ", Locale.US);
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.add(5, paramInt);
    localCalendar.set(10, this.mHour.intValue() + this.mDurationHours.intValue() - 1);
    localCalendar.set(12, 59);
    localCalendar.set(13, 59);
    return localSimpleDateFormat.format(localCalendar.getTime()).replaceAll(" ", "T");
  }
  
  private String getStartDate()
  {
    String str;
    switch (this.mDateRange.intValue())
    {
    default: 
      str = getStartOfDay(65171);
    }
    for (;;)
    {
      return str;
      str = getStartOfDay(0);
      continue;
      str = getStartOfDay(-1);
    }
  }
  
  private String getStartOfDay(int paramInt)
  {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ", Locale.US);
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.add(5, paramInt);
    localCalendar.set(10, this.mHour.intValue());
    localCalendar.set(12, 0);
    localCalendar.set(13, 0);
    return localSimpleDateFormat.format(localCalendar.getTime()).replaceAll(" ", "T");
  }
  
  public String getPath()
  {
    return "/network/:network/camera/:camera/videos";
  }
  
  public Map<String, String> getQuery()
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("start", getStartDate());
    localHashMap.put("end", getEndDate());
    if (this.mDateRange.intValue() > 0) {
      localHashMap.put("sig", getDateRange());
    }
    return localHashMap;
  }
  
  public int getRequestType()
  {
    return 0;
  }
  
  public Class getResponseClass()
  {
    return Videos.class;
  }
}


/* Location:              /home/zips/Android/Apktool/Blink4Home/Blink-136-dex2jar.jar!/com/immediasemi/blink/api/requests/Networks/GetVideosForCameraDates.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */