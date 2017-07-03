package com.kryptkode.cyberman.eritsmartdisplay.utils;

import com.kryptkode.cyberman.eritsmartdisplay.model.SmartDisplay;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cyberman on 7/3/2017.
 */

public class DataHelper {

    public static  SmartDisplay parseSyncedData(String data){
        String test = "//M11 Hello//M22 Welcome to erit.com.ng//M33 Welcome to erit.com.ng//" +
                "M44 Welcome to erit.com.ng//M55 Welcome to erit.com.ng//M66 Welcome to erit.com.ng//" +
                "M77 Hi//M88 Welcome8//P00000//A00000//D00000//S0000001//";

        SmartDisplay display = new SmartDisplay();
        return  display;
    }


}
