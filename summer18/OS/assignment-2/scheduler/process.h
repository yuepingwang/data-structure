//
// Created by Yueping Wang on 6/25/18.
//

#ifndef SCHEDULER_PROCESS_H
#define SCHEDULER_PROCESS_H
#include "enums.h"

class Process {
private:

public:
    int pid;
    int at, tc, cb, io;
    int prio, ft, tt, it, cw;//finish time, turnaround time, io time, cpu waiting time
    int rc;// remaining total cpu time

    int rcb = 0;//remaining cb time (preemption)


    state last_state;
    state cur_state;
    int state_timestamp;
    int time_in_prev_state;
    //initialized by input


    Process();
    Process(int _pid, int _at, int _tc, int _cb, int _io, int _prio){
        pid = _pid;
        at = _at;
        tc = _tc;
        cb = _cb;
        io = _io;
        prio = _prio;
        rc = _tc;// remaining cpu time = total cpu time
        it =0;
        cw =0;
    };
    int getAT();
    void pFinish(int);//update finish time. ft,tt, it, cw(cpu waiting)
    transition next_transition = TRANS_TO_READY;
    ~Process(){
        //
    };
};


#endif //SCHEDULER_PROCESS_H
