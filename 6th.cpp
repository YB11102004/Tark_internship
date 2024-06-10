#include<iostream>
#include<bits/stdc++.h>
using namespace std;
int main()
{
    string p1[]={"bcdbb","aaccd","dacbc","bcbda","cdedc","bbaaa","aecae"};
    string p2[]={"bcdbb","ddacb","aaccd","adcab","edbee","aecae","bcbda"};
    string p3[]={"dcaab","aadbe","bbaaa","ebeec","eaecb","bcbba","aecae","adcab","bcbda"};
    int pp1=0,pp2=0,pp3=0,j,i,flag1=0,flag2=0,flag3=0;
    //for p1
    for(i=0;i<sizeof(p1)/sizeof(p1[0]);i++)
    {
        for(j=0;j<sizeof(p2)/sizeof(p2[0]);j++)
        {
            if(p2[j]==p1[i])
            {
                flag2=1;
            }
        }
        for(j=0;j<sizeof(p3)/sizeof(p3[0]);j++)
        {
            if(p3[j]==p1[i])
            {
                flag3=1;
            }
        }
        if(flag2==1 && flag3==1)
        {
            pp1+=1;
        }
        else if(flag2==1 || flag3==1)
        {
            pp1+=2;
        }
        else
        {
            pp1+=3;
        }
        flag2=0;
        flag3=0;
    }
    cout<<pp1<<"/";
    // for p2
    for(i=0;i<sizeof(p2)/sizeof(p2[0]);i++)
    {
        for(j=0;j<sizeof(p1)/sizeof(p1[0]);j++)
        {
            if(p1[j]==p2[i])
            {
                flag1=1;
            }
        }
        for(j=0;j<sizeof(p3)/sizeof(p3[0]);j++)
        {
            if(p3[j]==p2[i])
            {
                flag3=1;
            }
        }
        if(flag1==1 && flag3==1)
        {
            pp2+=1;
        }
        else if(flag1==1 || flag3==1)
        {
            pp2+=2;
        }
        else
        {
            pp2+=3;
        }
        flag1=0;
        flag3=0;
    }
    cout<<pp2<<"/";
    //for p3
    for(i=0;i<sizeof(p3)/sizeof(p3[0]);i++)
    {
        for(j=0;j<sizeof(p2)/sizeof(p2[0]);j++)
        {
            if(p2[j]==p3[i])
            {
                flag2=1;
            }
        }
        for(j=0;j<sizeof(p1)/sizeof(p1[0]);j++)
        {
            if(p1[j]==p3[i])
            {
                flag1=1;
            }
        }
        if(flag2==1 && flag1==1)
        {
            pp3+=1;
        }
        else if(flag2==1 || flag1==1)
        {
            pp3+=2;
        }
        else
        {
            pp3+=3;
        }
        flag2=0;
        flag1=0;
    }
    cout<<pp3;
    return 0;
}
