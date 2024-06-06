#include <iostream>
#include <bits/stdc++.h>
using namespace std;
int main() {
    int n,i,b,j;
    cin>>n; // 3
    int count=0;
    double a[3]={6,2.5,3.5};
    double avg=0.0;
    b=sizeof(a)/sizeof(a[0]);
    if(b==n)
    {
        cout<<"0";
    }
    else
    {
        double c[b-n+1];
        for(i=0;i<b-n+1;i++)
        {
            for(j=i;j<n+i;j++)
            {
                avg+=a[j];
            }
            avg=avg/n;
            c[count++]=avg;
            avg=0.0;
        }
        int n1=b-n+1;
        double d=*max_element(c,c+n1);
        double e=*min_element(c,c+n1);
        cout<<d-e;
    }
    return 0;
}
