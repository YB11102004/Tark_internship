#include <iostream>
#include <bits/stdc++.h>
using namespace std;
int main() {
    int a[]={11, 11, 49, 7, 11, 11, 7, 7, 11, 49, 11};
    int b[]={11, 11, 49, 7, 11, 11, 7, 7, 11, 49, 11};
    int count=0;
    int n = sizeof(a)/sizeof(a[0]);
    sort(a,a+n);
    for(int i=0;i<n;i++)
    {
        if(a[i]!=b[i])
        {
            count++;
        }
    }
    cout<<count;
    return 0;
}
