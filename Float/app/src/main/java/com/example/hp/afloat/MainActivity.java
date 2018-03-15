package com.example.hp.afloat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{
    private EditText decimalNumber,floatingPointNumber;
    private TextView decimalError;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        decimalNumber=(EditText)findViewById(R.id.decimalNumber);
        decimalError=(TextView)findViewById(R.id.decimalError);
        floatingPointNumber=(EditText)findViewById(R.id.floatingPointNumber);
        decimalNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!isFloat(editable.toString()))
                    if(editable.toString().isEmpty()) {
                        decimalError.setVisibility(View.GONE);
                        floatingPointNumber.setText("");
                    }
                    else
                        decimalError.setVisibility(View.VISIBLE);
                else{
                    decimalError.setVisibility(View.GONE);
                    double num=Double.valueOf(editable.toString());
                    //将十进制数转换为32位浮点数格式
                    if(num==0){
                        floatingPointNumber.setText("0");
                    }
                    else{
                        char S;//符号处理
                        if(num>0)
                            S = '0';
                        else {
                            S = '1';
                            num=-num;
                        }
                        String M=doubleToBinary(num,25);
                        int e=M.indexOf('.')-M.indexOf('1')-1+127;
                        M=M.substring(M.indexOf('1')+1,M.length()-1);
                        M=M.replace(".","");
                        String E=Integer.toBinaryString(e);
                        for(int i=0;i<8-E.length();i++)
                            E='0'+E;
                        floatingPointNumber.setText(S+E+M);
                    }
                }
            }
        });
        floatingPointNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private boolean isFloat(String s) {                         //判断字符串s是否为小数
        int pointCount=0;
        if(s.isEmpty())
            return false;                                       //s为空则不是小数
        else {
            if(s.charAt(0)=='.')
                return false;                                   //小数点不能在第一位
            else if((s.length()==1||s.charAt(1)=='.')&&(s.charAt(0)=='+'||s.charAt(0)=='-'))
                return false;                                   //若有符号，则后面必须紧跟数字
            else{
                for(int i=1;i<s.length();i++){
                    if(s.charAt(i)=='+'||s.charAt(i)=='-')
                        return false;                           //除了第一位都不能是符号位
                    if(s.charAt(i)=='.')
                        pointCount++;
                    if(pointCount>1)
                        return false;                           //小数点最多只有一个
                }
            }
        }
        return true;
    }

    private String doubleToBinary(double n,int a){              //double型小数转换成二进制
        String lStr=Integer.toBinaryString((int)n);              // 整数部分
        /*
        String s = n + "";
        int p = s.length() - s.indexOf(".") -1;
        int a=(int)((double)p * Math.log(10)/Math.log(2))+1;
        */
        String rStr="";
        for (int i=0;i<a-lStr.length();i++){
            n=n-(int)n;//取小数
            n*=2;
            if(n>=1)
                rStr+='1';
            else
                rStr+='0';
        }
        return lStr+'.'+rStr;
    }
}
