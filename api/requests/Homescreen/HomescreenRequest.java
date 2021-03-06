package com.immediasemi.blink.api.requests.Homescreen;

import com.immediasemi.blink.api.requests.BlinkRequest;
import com.immediasemi.blink.models.HomescreenSummaryStatus;

public class HomescreenRequest
  extends BlinkRequest
{
  private static final String path = "/network/:network/homescreen";
  private static final long serialVersionUID = 7218049732725760121L;
  
  public String getPath()
  {
    return "/network/:network/homescreen";
  }
  
  public int getRequestType()
  {
    return 0;
  }
  
  public Class getResponseClass()
  {
    return HomescreenSummaryStatus.class;
  }
}


/* Location:              /home/zips/Android/Apktool/Blink4Home/Blink-136-dex2jar.jar!/com/immediasemi/blink/api/requests/Homescreen/HomescreenRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */