package cn.toseektech.example.netty;

public class Device {

	private String deviceId;

	private String deviceType;

	public Device(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Device) {
			return ((Device)obj).getDeviceId().equals(deviceId);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return this.deviceId.hashCode();
	}

}
