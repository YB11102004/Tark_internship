#include<iostream>
#include<bits/stdc++.h>
using namespace std;
int main()
{
    string s="001101100101100110111101011001011001010";
    string s1[]={"110","011","10","0011","00011","111","00010","0010","010","0000"};
    int len=1;
    string s3="",s2;
    int i,j=0,flag=0;
    while(j<s.length())
    {
        s2=s.substr(j,len);
        for(i=0;i<sizeof(s1)/sizeof(s1[0]);i++)
        {
            if(s2==s1[i])
            {
                s3+=char(i+65);
                flag=1;
            }
        }
        if(flag==1)
        {
            j=j+len;
            len=1;
            flag=0;
        }
        else
        {
            len++;
        }
    }
    cout<<s3;
    return 0;
}
