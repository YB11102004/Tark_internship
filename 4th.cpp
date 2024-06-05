#include <iostream>
#include <bits/stdc++.h>
using namespace std;
int main() {
    int pos[4]={0, 0, 0, 0};
    int len[4]={2, 4, 8, 16};
    string s;
    cin>>s; // "XY"
    for(int i=0;i<4;i++)
    {
        string s1=s.substr(pos[i],len[i]);
        reverse(s1.begin(),s1.end());
        int a=pos[i]+len[i];
        s.insert(a,s1);
    }
    cout<<s;
    return 0;
}
