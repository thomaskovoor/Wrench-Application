package com.example.wrenchapp.datamodel;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CommonService {

    public List<Object> parseNucleusData(List<List<Common_Format>> commonList, Object object)
    {


        Field[] fields=object.getClass().getDeclaredFields();
        String typeName=object.getClass().getName();
        Type type;
        Class<?> clazz;
        Constructor<?> ctor=null;
        try {
            clazz = Class.forName(typeName);
            TypeToken<?> typeToken = TypeToken.get(clazz);
            type=typeToken.getType();
            ctor = clazz.getConstructor();
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Unsupported type: " + typeName);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }


        List<Object> objectList=new ArrayList<>();
// Log.d("Type",type+"");

        for(int i=0;i<commonList.size();i++)
        {
            Object object1 = null;
            try
            {
                object1=ctor.newInstance(new Object[] { });
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            for(int j=0;j<commonList.get(i).size();j++)
            {
                for(Field field:fields)
                {
                    String name=field.getName();
                    field.setAccessible(true);
                    if(commonList.get(i).get(j).getFieldName().equalsIgnoreCase(name))
                    {
                        try
                        {
                            if(commonList.get(i).get(j).getValue()!=null)
                            {
                                if(commonList.get(i).get(j).getValueType()==0)
                                {
                                    field.set(object1,Integer.parseInt(commonList.get(i).get(j).getValue()));
                                }
                                else
                                {
                                    field.set(object1,commonList.get(i).get(j).getValue());
                                }

                            }
                        }
                        catch (Exception e)
                        {
                            Log.d("e",e.getMessage());
                        }
                    }
                }
            }
            Gson gson=new Gson();
            String str=gson.toJson(object1);

            Log.d("value",object1+"");
            objectList.add(object1);
        }
        Gson gson=new Gson();
        String str=gson.toJson(objectList);
        return objectList;
    }
    public String DateParsor(String str) {
        int i;
        if(str.contains("-"))
        {
            i = str.indexOf('-');
        }
        else
        {
            i = str.indexOf('+');
        }
        str = str.substring(0, i);
        long val = Long.parseLong(str.replaceAll("\\D", ""));
        Date date = new Date(val);
        DateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy hh:mm aaa", Locale.getDefault());
        String dateFormatted = formatter.format(date);
        return dateFormatted;
    }

}
