package com.rosemellow.alternatecreditscore;

import android.app.AppOpsManager;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class TermsandConditionsActivity extends BaseActivity {

    Button proceedbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termsand_conditions);

        proceedbtn=findViewById(R.id.proceedbtn);
        proceedbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

     /*           startActivity(new Intent(TermsandConditionsActivity.this, CommonFetchingActivity.class));
                finish();*/

                if (abc()) {
                    startActivity(new Intent(TermsandConditionsActivity.this,
                            StartCommonFetchingActivity.class));
                } else {
                    if (Build.VERSION.SDK_INT >= 21) {
                        UsageStatsManager mUsageStatsManager = (UsageStatsManager) TermsandConditionsActivity.this.getSystemService(Context.USAGE_STATS_SERVICE);
                        long time = System.currentTimeMillis();
                        List stats = mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000 * 10, time);

                        if (stats == null || stats.isEmpty()) {
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_USAGE_ACCESS_SETTINGS);
                            startActivity(intent);


                        }
                    }
                }


            }
        });
    }




    public Boolean abc() {
        try {
            PackageManager packageManager = TermsandConditionsActivity.this.getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(TermsandConditionsActivity.this.getPackageName(), 0);
            AppOpsManager appOpsManager = (AppOpsManager) TermsandConditionsActivity.this.getSystemService(Context.APP_OPS_SERVICE);
            int mode = appOpsManager.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, applicationInfo.uid, applicationInfo.packageName);
            return (mode == AppOpsManager.MODE_ALLOWED);

        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }

    }

}