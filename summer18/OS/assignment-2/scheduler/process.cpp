//
// Created by Yueping Wang on 6/25/18.
//
#include "enums.h"
#include "process.h"
#include <iostream>
#include <fstream>
#include <string>

using namespace std;

Process::Process() = default;

Process::Process(int _pid, int _at, int _tc, int _cb, int _io){
    pid = _pid;
    at = _at;
    tc = _tc;
    cb = _cb;
    io = _io;
};

void Process::pFinish(int _ft){
    ft = _ft;
    tt = ft-at;
};

int Process::getAT(){
    return at;
};