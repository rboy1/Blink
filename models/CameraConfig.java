package com.immediasemi.blink.models;

public class CameraConfig
  extends BlinkData
{
  private static final long serialVersionUID = -9018191751718611034L;
  protected CameraConfigInfo[] camera;
  
  public CameraConfigInfo[] getCameraConfig()
  {
    return this.camera;
  }
  
  public void setCameraConfig(CameraConfigInfo[] paramArrayOfCameraConfigInfo)
  {
    this.camera = paramArrayOfCameraConfigInfo;
  }
}


/* Location:              /home/zips/Android/Apktool/Blink4Home/Blink-136-dex2jar.jar!/com/immediasemi/blink/models/CameraConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */