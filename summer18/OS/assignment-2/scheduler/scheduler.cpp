//
// Created by Yueping Wang on 6/24/18.
//

#include "scheduler.h"
using namespace std;

//scheduler::scheduler()= default;

//FCFSscheduler::FCFSscheduler()= default;

FCFSscheduler::FCFSscheduler(int q){
    my_quantum = q;
};

void FCFSscheduler::add_to_queue(Process *p){
    //first update the time stamp and data of process
    //...
    //add the ready-state process to the ready queue
    ready_queue.push_back(p);
};

Process* FCFSscheduler::get_next_process(){
    //check if queue is empty (return nullptr)
    if(ready_queue.empty())
        cerr<<"Ready Queue is Empty"<<endl;
    //otherwise return front of queue
    else return readyqueue.front();
};


RRscheduler::RRscheduler() = default;
RRscheduler::RRscheduler(int quantum){

};

void RRscheduler::add_to_queue(Process *p){

}

Process* RRscheduler::get_next_process(){
    //check if queue is empty (return nullptr)
    //otherwise return front of queue
};