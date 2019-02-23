//Questions:
//linker error, signiture not defined?
//iterator -> pointer -> method?

#include "enums.h"
#include "process.h"
#include "event.h"
#include "scheduler.h"

#include <iostream>
#include <fstream>
#include <string>
#include <queue>
#include<list>

using namespace std;

//parsing random number. first get the array size
int rand_count;

//Global variables
bool call_scheduler = false;
int cur_time;

int myrandom(int p_burst, int rand, int & walk) {
    walk++;
    return 1 + (rand % p_burst);
}

void simulate(){
    Event * evt;
    //while()
}

list<Process> processes;
//scheduler my_scheduler;//?is this saved on the heap?
//?Question on casting

int main() {
    //get rand_file count, allocate memory
    string rfilename = "/Users/yuepingwang/Desktop/summer18/OS/assignment-2/lab2_assign/rfile";
    string infilename = "/Users/yuepingwang/Desktop/summer18/OS/assignment-2/lab2_assign/input0";

    fstream rfile(rfilename, ios_base::in);
    rfile >> rand_count;
    int * rand_array;//ptr to array of rand numbers
    rand_array = new int[rand_count];//malloc

    //load numbers to array
    for(int i = 0; i<rand_count; i++){
        rfile >> rand_array[i];
    }
    int rand_walk = 0; //index of the next rand number to use

    //parse process to list
    fstream infile(infilename, ios_base::in);
    int at, tc, cb, io;
    int pid=0;
    while(infile >> at){
        infile >>tc;
        infile >>cb;
        infile >>io;
        int prio = myrandom(4, rand_array[rand_walk], rand_walk);
        Process newP(pid, at, tc, cb, io, prio);
        newP.cur_state = CREATED;
        newP.state_timestamp = at;
        processes.push_back(newP);
        pid++;
    }
    //use each process's burst time and the rand array to get the actual burst time

    //INITIALIZE A SCHEDULER w/quantum
    FCFSscheduler myscheduler(10000);
    //INITIALIZE DES LAYER
    DES myDES;

    //GET CURRENT-TIME FROM FIRST PROCESS
    cur_time = processes.front().at;
    cout<<"cur time is: "<<cur_time<<endl;
    //Process p = processes.front();
    myscheduler.total_p = (int)processes.size();
    cout<<"Number of processes is: "<<myscheduler.total_p<<endl;

    //INITIALIZE READYQUEUE FOR READY-STATE PROCESSES
    for(auto it = processes.begin(); it != processes.end(); it++){
        cout<<"at is: "<<(*it).at<<endl;
        cout<<"pid is: "<<(*it).pid<<endl;
        cout<<"cpu burst is: "<<(*it).cb<<endl;
        cout<<"cur state is : "<<(*it).cur_state<<endl;
        // if at = cur_time && state == CREATED, create an event for this process
        if((*it).at == cur_time){
            (*it).last_state = (*it).cur_state;
            (*it).cur_state = READY;
            (*it).state_timestamp=cur_time;
            myscheduler.add_to_queue(&(*it));
        }
        else{
            myscheduler.add_to_ready(&(*it));
        }
    }

    Process * curP;
    int rand_cb, rand_io;

    // CREATE INITIAL EVENTS
    curP = myscheduler.get_next_process();
    //cout<<cur_time<<" "<<curP->pid<<" "<<curP->time_in_prev_state<<": "<<curP->last_state<<" "<<curP->cur_state<<endl;
    while(curP != NULL){
        cout<<cur_time<<" "<<curP->pid<<" "<<curP->time_in_prev_state<<": "<<curP->last_state<<" "<<curP->cur_state<<endl;
        myDES.addEvent(curP, cur_time, TRANS_TO_RUN);
        curP = myscheduler.get_next_process();
    }
    //SIMULATE
    Event curE;
    while( myDES.getEvent(curE) ){
        //CUR_TIME <- TIMESTAMP OF THE EVENT
        Process * evtP = curE.my_process;
        curP = evtP;
        cur_time = max(curE.my_timestamp, cur_time);

        //UPDATE EVENT PARAMS
        evtP->time_in_prev_state=cur_time - evtP->state_timestamp;
        evtP->last_state = evtP->cur_state;
        evtP->state_timestamp = cur_time;
        //cout<<"Cur_time: "<<cur_time<<endl;
        //cout<<"STATE timestamp: "<<evtP->state_timestamp<<endl;
        //UPDATE CUR STATE
        switch(curE.my_transition){
            case TRANS_TO_READY:
                // ADD PROCESS BACK TO RUNQUEUE
                evtP->cur_state = READY;
                myscheduler.add_to_queue(evtP);
                call_scheduler = true;
                cout<<cur_time<<" "<<evtP->pid<<" "<<evtP->time_in_prev_state<<": "<<evtP->last_state<<" to "<<evtP->cur_state<<endl;
                break;
            case TRANS_TO_RUN: {
                // CREATE NEXT EVENT OF THIS PROCESS
                // IF A PROCESS RUNS FOR THE FIRST TIME, GET ITS CPU-WAITING TIME
                if(evtP->tc == evtP->rc)
                    evtP->cw = cur_time - evtP->at;
                // UPDATE STATE
                evtP->cur_state = RUNNG;

                //GET CPU BURST TIME
                if(evtP->rcb ==0){
                    evtP->rcb = myrandom(evtP->cb, rand_array[rand_walk], rand_walk);
                    evtP->rcb = min(evtP->rc, evtP->rcb);
                }
                cout<<cur_time<<" "<<evtP->pid<<" "<<evtP->time_in_prev_state<<": "<<evtP->last_state<<" to "<<evtP->cur_state<<" rcb= "<<evtP->rcb<<endl;

                //TRANSITION TO PREEMPT
                if(evtP->rcb>myscheduler.my_quantum){
                    myDES.addEvent(evtP, cur_time + myscheduler.my_quantum, TRANS_TO_PREEMPT);
                    evtP->rc -= myscheduler.my_quantum;
                    evtP->rcb -= myscheduler.my_quantum;
                }
                //ELSE TRANSITION TO BLOCK
                else{
                    evtP->rc -= evtP->rcb;
                    myDES.addEvent(evtP, cur_time + evtP->rcb, TRANS_TO_BLOCK);
                    evtP->rcb = 0;
//                    if (evtP->rc<=evtP->rcb){
//                        evtP->rcb= evtP->rc;
//                        myDES.addEvent(evtP, cur_time + evtP->rc, TRANS_TO_BLOCK);
//                        evtP->rc=0;
//                    }
//                    else{
//                    }
                }
                }
                break;
            case TRANS_TO_BLOCK:
                // MOVE PROCESS TO "BLOCK"
                //cout<<" RC OF PROCESS: "<<evtP->pid<<" IS: ["<<evtP->rc<<"]"<<endl;
                if(evtP->rc>0){
                    // GET IO BURST TIME
                    rand_io = myrandom(evtP->io, rand_array[rand_walk], rand_walk);
                    // UPDATE PROCESS TOTAL IO TIME
                    evtP->it += rand_io;
                    cout<<"TOTOAL IO TIME IS"<<evtP->it<<endl;
                    // UPDATE STATE
                    evtP->cur_state = BLOCK;
                    cout<<cur_time<<" "<<evtP->pid<<" "<<evtP->time_in_prev_state<<": "<<evtP->last_state<<" to "<<evtP->cur_state<<" io="<<rand_io<<endl;
                    // CREATE EVENT FOR WHEN PROCESS BECOMES READY AGAIN
                    myDES.addEvent(curP, cur_time + rand_io, TRANS_TO_RUN);
                }
                else{
                    // EVENT IS FINISHED
                    evtP->cur_state = Done;
                    cout<<cur_time<<" "<<evtP->pid<<" "<<evtP->time_in_prev_state<<": "<<evtP->last_state<<" to "<<evtP->cur_state<<endl;
                    for (auto it = processes.begin();it != processes.end();it++){
                        if ((*it).pid == evtP->pid){
                            (*it).ft = cur_time;
                            cout<<"FINISH TIME IS"<<(*it).ft<<endl;
                            (*it).tt = cur_time-(*it).at;
                            //processes.erase(it);
                            break;
                        }
                    }
                    // myscheduler.remove_finished_process(evtP, cur_time);
                    //cout<<"PROCESS REMOVED"<<endl;
                }
                call_scheduler = true;
                break;
            case TRANS_TO_PREEMPT:
                //ADD PROCESS BACK TO RUNQUEUE
                cout<<cur_time<<" "<<evtP->pid<<" "<<evtP->time_in_prev_state<<": "<<evtP->last_state<<" to "<<evtP->cur_state<<endl;
                myscheduler.add_to_queue(evtP);
                call_scheduler = true;
                break;
        }

        //Delete event?

        //cout<<"Total # of events"<<myDES.getEventsSize()<<endl;

        if(call_scheduler){
            if(myDES.getNextEventTime() <= cur_time && myDES.getNextEventTime() != -1)
                continue;

            call_scheduler=false;

            // CREATE AN EVENT FOR THE NEXT PROCESS IN RUNQUEUE

            curP = myscheduler.get_next_process();
            // IF NO PROCESS IN READY QUEUE
            if(curP == NULL){
                // CHECK THE CREATED QUEUE
                if (myscheduler.hasCreatedProcess()){
                    if(myDES.getEventsSize()!=0){
                        int nextEventStateTS = myDES.getNextEventStateTS();
                        //FOR EACH CREATED EVENT
                        // COMPARE NEXT-EVENT'S-STATE-TIME W/ NEXT-PROCESS-ARRIVAL-TIME
                        for (auto it = myscheduler.created_queue.begin();it != myscheduler.created_queue.end();it++){
                            // IF NEXT-EVENT'S-STATE-TIME BEFORE PROCESS, BREAK (do the event first)
                            if(nextEventStateTS < (*it)->at)
                                break;
                            // ELSE, MOVE PROCESS TO READYQUEUE, CONTINUE...
                            else
                                myscheduler.created_to_ready((*it),cur_time);
                        }
                        continue;
                    }
                    else{
                        auto it = myscheduler.created_queue.begin();
                        cur_time = max((*it)->at, cur_time);
                        myscheduler.created_to_ready((*it), cur_time);
                    }
                }
                else
                    continue;
            }
            //create an event
            curP = myscheduler.get_next_process();
            cout<<"ADD NEW PROCESS TO READY QUEUE pid="<<curP->pid<<endl;
            myDES.addEvent(curP, cur_time, TRANS_TO_RUN);
            //determine what event/transtion to generate
//            switch(curP->cur_state){
//
//            }
            //myDES.addEvent()

        }

    }
    //Print result
    for (auto it = processes.begin(); it != processes.end();it++)
        printf("%d: %d %d %d %d | %d %d %d %d \n",(*it).pid, (*it).at, (*it).cb, (*it).io, (*it).prio, (*it).ft, (*it).tt, (*it).it, (*it).cw);

    //delete &myscheduler;
    delete[]rand_array;
    return 0;
}