package com.nadeemsultan.hidecarrierlabel;

import android.view.View;
import android.widget.TextView;
import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.callbacks.XC_InitPackageResources.InitPackageResourcesParam;
import de.robv.android.xposed.callbacks.XC_LayoutInflated;

public class Main implements IXposedHookZygoteInit, IXposedHookInitPackageResources {
	private static String MODULE_PATH = null;

    @Override
    public void initZygote(StartupParam startupParam) throws Throwable {
        MODULE_PATH = startupParam.modulePath;
    }

    @Override
    public void handleInitPackageResources(InitPackageResourcesParam resparam) throws Throwable {
        if (!resparam.packageName.equals("com.android.systemui"))
            return;

        resparam.res.hookLayout("com.android.systemui", "layout", "keyguard_status_bar", new XC_LayoutInflated() {
            @Override
            public void handleLayoutInflated(LayoutInflatedParam liparam) throws Throwable {
                TextView keyguard_carrier_text = (TextView) liparam.view.findViewById(
                        liparam.res.getIdentifier("keyguard_carrier_text", "id", "com.android.systemui"));
                keyguard_carrier_text.setVisibility(View.GONE);
            }
        }); 
    }
}