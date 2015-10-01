/**

 OAPMessenger.class is class that implements "30 pin" serial protocol
 for iPod. It is based on the protocol description available here:
 http://www.adriangame.co.uk/ipod-acc-pro.html

 Copyright (C) 2015, Roman P., dev.roman [at] gmail

 This program is free software; you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation; either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program; if not, write to the Free Software Foundation,
 Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA

 */

package com.rp.podemu;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by rp on 9/10/15.
 */
public class PodEmuLog
{
    /**
     * Debug levels:
     * 1 - log
     * 2 - debug
     * 3 - verbose debug
     */
    public final static int DEBUG_LEVEL=2;
    public static Context context;

    public final static String TAG="PodEmu";


    private static FileOutputStream logfileStream;
    private static File logfile;
    private String filename="PodEmu_" + System.currentTimeMillis() + ".log";

    public PodEmuLog(Context c)
    {
        context=c;


        String dirname="PodEmuLogs";
        // Get the directory for the user's public pictures directory.
        File logdir = new File(Environment.getExternalStorageDirectory(), dirname);
        PodEmuLog.log("Log dir: " + logdir.getPath());
        if (!logdir.mkdirs())
        {
            log("Directory not created");
        }
        logfile=new File(logdir,filename);

        try
        {
            logfileStream = new FileOutputStream(logfile);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public static void log(String str, boolean isError)
    {
        if(isError)
            Log.e(TAG, str);
        else
            Log.d(TAG, str);

        if(logfileStream!=null)
        {
            byte msg[]=(System.currentTimeMillis() + ": " + TAG + " - " + str + "\n").getBytes();
            try
            {
                logfileStream.write(msg, 0, msg.length);
                logfileStream.flush();
            }
            catch (java.io.IOException e)
            {
                e.printStackTrace();
            }
        }

    }

    public static void log(String str)
    {
        log(str, false);
    }

    public static void debug(String str)
    {
        if(DEBUG_LEVEL<2) return;
        log(str);

    }
    public static void verbose(String str)
    {
        if(DEBUG_LEVEL<3) return;
        log(str);
    }

    public static void error(String str)
    {
        log("ERROR: " + str, true);
    }




}