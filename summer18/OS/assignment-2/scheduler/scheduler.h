//
// Created by Yueping Wang on 6/24/18.
//

#ifndef SCHEDULER_SCHEDULER_H
#define SCHEDULER_SCHEDULER_H
#include "enums.h"
#include "process.h"

//Interface for schedulers
class scheduler {
private:
    //ready queue
    std::list <Process *> ready_queue;

public:
    int my_quantum;
//    scheduler();
//    scheduler(int q){
//        my_quantum = q;
//    };
    //virtual functions
    virtual void add_to_queue(Process *p) = 0;
    virtual Process* get_next_process() = 0;
    //static function for event queue?
};



//FCFS
class FCFSscheduler : public scheduler{
private:
    std::list <Process *> ready_queue;
public:
    int my_quantum;
    int total_p;
    std::list <Process *> created_queue;
    //constructor
    FCFSscheduler() : scheduler(){
        //
    };
    FCFSscheduler(int q){
        my_quantum = q;
    };
    //FCFSscheduler(int quantum){};
    //implement virtual functions
    void add_to_queue(Process *p){

        ready_queue.push_back(p);
    };

    void add_to_ready(Process *p){

        created_queue.push_back(p);
    }

    bool hasCreatedProcess(){
        return (created_queue.size() != 0);
    }
    void created_to_ready(Process *created,int cur_time){
        for (auto it = created_queue.begin();it != created_queue.end();it++)
            if ((*it)->pid == created->pid){
                //ENQUEUE
                (*it)->last_state = (*it)->cur_state;
                (*it)->cur_state = READY;
                (*it)->state_timestamp = cur_time;
                //(*it)->time_in_prev_state = cur_time - (*it)->state_timestamp;
                ready_queue.push_back((*it));
                //DEQUEUE
                created_queue.erase(it);
                return;
            }
    }

    Process* get_next_process(){
        //check if queue is empty (return nullptr)
        if(ready_queue.empty()){
            std::cerr<<"Ready Queue is Empty"<<std::endl;
            return NULL;
        }
        //otherwise dequeue it from front of ready_queue
        else {
            Process * p = ready_queue.front();
            ready_queue.pop_front();
            return p;
        }
    };

//    void remove_finished_process(Process *finished, int cur_time){
//        for (auto it = processes.begin;it != ready_queue.end();it++){
//            if ((*it)->pid == finished->pid){
//                //UPDATE PROCESS PARAMS
//                (*it)->ft = cur_time;
//                std::cout<<"FINISH TIME IS"<<(*it)->ft<<std::endl;
//                (*it)->tt = (*it)->at - cur_time;
//                //DEQUEUE FROM READYQUEUE
//                ready_queue.erase(it);
//                break;
//            }
//        }
//    }
    ~FCFSscheduler(){
        //
    };
};





//ROUND ROBIN
//class RRscheduler : public scheduler{
//private:
//    int my_quantum;
//public:
//    //constructor
//    RRscheduler(){};
//    RRscheduler(int quantum){};
//    //implement virtual functions
//    void add_to_queue(Process *p);
//    Process* get_next_process();
//};








//FCFS
//class FCFSscheduler : public Scheduler{
//private:
//    //ready queue
//    queue <Process *p> readyQueue;
//public:
//    void add_to_queue(Process *p);
//    Process* get_next_process();
//};

//LCFS


//SJF

//PRIO
#endif //SCHEDULER_SCHEDULER_H
