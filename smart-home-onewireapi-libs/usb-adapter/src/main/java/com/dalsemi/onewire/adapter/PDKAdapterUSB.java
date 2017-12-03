package com.dalsemi.onewire.adapter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;

import com.dalsemi.onewire.OneWireException;
import com.dalsemi.onewire.adapter.DSPortAdapter;
import com.dalsemi.onewire.adapter.OneWireIOException;

import cz.adamh.utils.NativeUtils;

public class PDKAdapterUSB extends DSPortAdapter {

	private int port_handle = -1;
	private String port_name = "";
	private Object syncObj = new Object();
	private static Object staticSyncObj = new Object();
	private static boolean libLoaded = false;
	private boolean inExclusive = false;

	public PDKAdapterUSB() {
		synchronized (staticSyncObj) {
			if (!libLoaded) {
				try {
					NativeUtils.loadLibraryFromJar("/libs/libonewireUSB.so");
				} catch (IOException e) {
					System.loadLibrary("onewireUSB");	
				}
				libLoaded = true;
			}
		}
		this.inExclusive = false;
	}

	private native int OpenPort(String paramString);

	public boolean selectPort(String portName) throws OneWireIOException, OneWireException {
		try {
			int portnumber = Integer.parseInt(portName.substring(3));
			this.port_handle = OpenPort("DS2490-" + portnumber);
			this.port_name = portName;
			this.inExclusive = false;
			return this.port_handle != -1;
		} catch (Exception e) {
			throw new OneWireException("Bad Portnumber: " + portName);
		}
	}

	private native void ClosePort(int paramInt);

	public void freePort() throws OneWireException {
		if (this.port_handle != -1) {
			ClosePort(this.port_handle);
			this.port_handle = -1;
			this.port_name = "";
			this.inExclusive = false;
		}
	}

	public String getAdapterName() {
		return "DS9490";
	}

	public String getPortTypeDescription() {
		return "USB Adapter with libUSB and PDK API";
	}

	public String getClassVersion() {
		return "USB-Beta";
	}

	public Enumeration getPortNames() {
		Vector v = new Vector();
		for (int i = 1; i < 15; i++) {
			v.addElement("USB" + i);
		}
		return v.elements();
	}

	public String getPortName() throws OneWireException {
		return this.port_name;
	}

	private native boolean AdapterDetected(int paramInt);

	public boolean adapterDetected() throws OneWireIOException, OneWireException {
		if (this.port_handle != -1) {
			return AdapterDetected(this.port_handle);
		}
		throw new OneWireException("Port not selected");
	}

	private native void GetAddress(int paramInt, byte[] paramArrayOfByte);

	public void getAddress(byte[] address) {
		if (this.port_handle != -1) {
			GetAddress(this.port_handle, address);
		}
	}

	private native int Search(int paramInt, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3);

	public boolean findFirstDevice() throws OneWireIOException, OneWireException {
		if (this.port_handle != -1) {
			int ret = Search(this.port_handle, true, !this.searchNoReset, this.searchAlarmOnly);
			if (ret == -1) {
				throw new OneWireException("Adapter communication error during search");
			}
			return ret == 1;
		}
		throw new OneWireException("Port not selected");
	}

	public boolean findNextDevice() throws OneWireIOException, OneWireException {
		if (this.port_handle != -1) {
			int ret = Search(this.port_handle, false, !this.searchNoReset, this.searchAlarmOnly);
			if (ret == -1) {
				throw new OneWireException("Adapter communication error during search");
			}
			return ret == 1;
		}
		throw new OneWireException("Port not selected");
	}

	private boolean searchAlarmOnly = false;
	private boolean searchNoReset = false;

	public void setSearchOnlyAlarmingDevices() {
		this.searchAlarmOnly = true;
	}

	public void setNoResetSearch() {
		this.searchNoReset = true;
	}

	public void setSearchAllDevices() {
		this.searchAlarmOnly = false;
		this.searchNoReset = false;
	}

	public boolean beginExclusive(boolean blocking) throws OneWireException {
		boolean gotExclusive = false;
		while ((!gotExclusive) && (blocking)) {
			synchronized (this.syncObj) {
				if (!this.inExclusive) {
					this.inExclusive = true;
					gotExclusive = true;
				}
			}
		}
		return false;
	}

	public void endExclusive() {
		this.inExclusive = false;
	}

	private native int TouchBit(int paramInt1, int paramInt2);

	private native int TouchBitPower(int paramInt1, int paramInt2);

	public void putBit(boolean dataBit) throws OneWireIOException, OneWireException {
		if (this.port_handle != -1) {
			int ret;
			if ((this.levelChangeOnNextBit) && (this.primedLevelValue == 1)) {
				ret = TouchBitPower(this.port_handle, dataBit ? 1 : 0);
			} else {
				ret = TouchBit(this.port_handle, dataBit ? 1 : 0);
			}
			this.levelChangeOnNextBit = false;
			if (ret == -1) {
				throw new OneWireIOException("1-Wire Adapter Communication Failed During Touchbit");
			}
			if (ret != (dataBit ? 1 : 0)) {
				throw new OneWireIOException("PutBit failed, echo did not match");
			}
		} else {
			throw new OneWireException("Port not selected");
		}
	}

	public boolean getBit() throws OneWireIOException, OneWireException {
		if (this.port_handle != -1) {
			int ret;
			if ((this.levelChangeOnNextBit) && (this.primedLevelValue == 1)) {
				ret = TouchBitPower(this.port_handle, 1);
			} else {
				ret = TouchBit(this.port_handle, 1);
			}
			this.levelChangeOnNextBit = false;
			if (ret == 1) {
				return true;
			}
			if (ret == 0) {
				return false;
			}
			throw new OneWireIOException("1-Wire Adapter Communication Failed During Touchbit");
		}
		throw new OneWireException("Port not selected");
	}

	private native int TouchByte(int paramInt1, int paramInt2);

	private native int TouchBytePower(int paramInt1, int paramInt2);

	public void putByte(int dataByte) throws OneWireIOException, OneWireException {
		if (this.port_handle != -1) {
			int ret;
			if ((this.levelChangeOnNextByte) && (this.primedLevelValue == 1)) {
				ret = TouchBytePower(this.port_handle, dataByte);
			} else {
				ret = TouchByte(this.port_handle, dataByte);
			}
			this.levelChangeOnNextByte = false;
			if (ret == -1) {
				throw new OneWireIOException("1-Wire Adapter Communication Failed During Touchbyte");
			}
		} else {
			throw new OneWireException("Port not selected");
		}
	}

	public int getByte() throws OneWireIOException, OneWireException {
		if (this.port_handle != -1) {
			int ret;
			if ((this.levelChangeOnNextByte) && (this.primedLevelValue == 1)) {
				ret = TouchBytePower(this.port_handle, 255);
			} else {
				ret = TouchByte(this.port_handle, 255);
			}
			this.levelChangeOnNextByte = false;
			if (ret == -1) {
				throw new OneWireIOException("1-Wire Adapter Communication Failed During Touchbyte");
			}
			return ret;
		}
		throw new OneWireException("Port not selected");
	}

	public byte[] getBlock(int len) throws OneWireIOException, OneWireException {
		byte[] buff = new byte[len];
		getBlock(buff, 0, len);
		return buff;
	}

	public void getBlock(byte[] buff, int len) throws OneWireIOException, OneWireException {
		getBlock(buff, 0, len);
	}

	public void getBlock(byte[] buff, int off, int len) throws OneWireIOException, OneWireException {
		for (int i = 0; i < len; i++) {
			buff[(i + off)] = -1;
		}
		dataBlock(buff, off, len);
	}

	private native int DataBlock(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3);

	public void dataBlock(byte[] buff, int off, int len) throws OneWireIOException, OneWireException {
		if (this.port_handle != -1) {
			int ret = DataBlock(this.port_handle, buff, off, len);
			if (ret == -1) {
				throw new OneWireIOException("1-Wire Adapter Communication Failed During Reset");
			}
		} else {
			throw new OneWireException("Port not selected");
		}
	}

	private native int Reset(int paramInt);

	public int reset() throws OneWireIOException, OneWireException {
		if (this.port_handle != -1) {
			int ret = Reset(this.port_handle);
			if (ret == -1) {
				throw new OneWireIOException("1-Wire Adapter Communication Failed During Reset");
			}
			return ret;
		}
		throw new OneWireException("Port not selected");
	}

	public boolean canOverdrive() throws OneWireIOException, OneWireException {
		return false;
	}

	public boolean canHyperdrive() throws OneWireIOException, OneWireException {
		return false;
	}

	public boolean canFlex() throws OneWireIOException, OneWireException {
		return false;
	}

	public boolean canProgram() throws OneWireIOException, OneWireException {
		return false;
	}

	public boolean canDeliverPower() throws OneWireIOException, OneWireException {
		return true;
	}

	public boolean canDeliverSmartPower() throws OneWireIOException, OneWireException {
		return false;
	}

	public boolean canBreak() throws OneWireIOException, OneWireException {
		return false;
	}

	public void setPowerDuration(int timeFactor) throws OneWireIOException, OneWireException {
		if (timeFactor != 5) {
			throw new OneWireException("USerialAdapter-setPowerDuration, does not support this duration, infinite only");
		}
		this.levelTimeFactor = 5;
	}

	int levelTimeFactor = 5;
	int primedLevelValue = 0;
	boolean levelChangeOnNextByte = false;
	boolean levelChangeOnNextBit = false;

	public boolean startPowerDelivery(int changeCondition) throws OneWireIOException, OneWireException {
		if (changeCondition == 1) {
			this.levelChangeOnNextBit = true;
			this.primedLevelValue = 1;
			return true;
		}
		if (changeCondition == 2) {
			this.levelChangeOnNextByte = true;
			this.primedLevelValue = 1;
			return true;
		}
		if (changeCondition == 0) {
			int ret = PowerLevel(this.port_handle, 1);
			if (ret == 1) {
				return true;
			}
			if (ret == 0) {
				return false;
			}
			throw new OneWireIOException("1-Wire Adapter Communication Failed");
		}
		throw new OneWireException("Invalid power delivery condition");
	}

	public native int PowerLevel(int paramInt1, int paramInt2);

	public void setPowerNormal() {
		this.levelChangeOnNextByte = false;
		this.levelChangeOnNextBit = false;
		this.primedLevelValue = 0;
		PowerLevel(this.port_handle, 0);
	}
}
