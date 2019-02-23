//
// Created by Yueping Wang on 6/26/18.
//

#ifndef SCHEDULER_EVENT_H
#define SCHEDULER_EVENT_H
#include "enums.h"
#include "process.h"
#include <string>
#include<list>

class Event {
public:
    //data field
    int my_timestamp;
    //std::string old_state;
    //std::string new_state;
    Process * my_process;
    transition my_transition;
    //constructor
    Event()= default;
    Event(Process * _process, int _ts, transition _transition){
        my_process = _process;
        my_timestamp = _ts;
        my_transition = _transition;
    };
    //Operator overloading
//    bool operator=(const Event & This, Event & That){
//    Event & operator=( Event & That){
//        Event * Eptr = dynamic_cast<Event *>(&That);
//        if(Eptr != 0){
////            This->my_process = That->my_process;
////            This->my_next_ready_time = That->my_next_ready_time;
////            This->my_transition = That->my_transition;
//            this.my_process = That.my_process;
//            this.my_next_ready_time = That.my_next_ready_time;
//            this.my_transition = That.my_transition;
//            return this;
//        }
//        //else return false;
//    }
//    Event * operator=(Event * That){
//        Event * Eptr = dynamic_cast<Event *>(That);
//        if(Eptr != 0){
//            this->my_process = That->my_process;
//            this->my_next_ready_time = That->my_next_ready_time;
//            this->my_transition = That->my_transition;
////            this->my_process = That.my_process;
////            this->my_next_ready_time = That.my_next_ready_time;
////            this->my_transition = That.my_transition;
//        }
//    }
};

class DES {
private:
    std::list<Event> events;
    void sortByTimestamp(){};
public:
    DES(){
        //
    };
    bool getEvent(Event & evt){
        if( events.size()>0){
            //ADD FUNCTION TO SEARCH THROUGH EVENTQUEUE BY TIMESTAMP
//            int min_finish_time = 10000;
//            Event * next_event;
//            for (auto it  = events.begin(); it != events.end; it++){
//                if(it->my_timestamp<min_finish_time){
//                    min_finish_time = it->my_timestamp;
//                    next_event = &(*it);
//                }
//            }
//            for (auto it  = events.begin(); it != events.end; it++){
//                if(next_event ==&(*it)){
//                    next_event;
//                }
//            }
            evt = events.front();
            events.pop_front();
            //UPDATE EVENT TIMESTAMP
            //
            //GET RANDOM NUMBER TO GIVE TO CB/IO
            //
            //UPDATE EVENT TRANSITION
            //
            //UPDATE PROCESS VAR: IT (TIME IN BLOCKED STATE)
            //
            //UPDATE PROCESS STATE: CUR_STATE, PREV_STATE
            return true;
        }
        else return false;
    };
    int getNextEventTime(){
        if( events.size()>0){
            int time = events.front().my_timestamp;
            return time;
//            int min_finish_time = 10000;
//            for (auto it  = events.begin(); it != events.end; it++){
//                if(it->my_timestamp<min_finish_time){
//                    min_finish_time = it->my_timestamp;
//                }
//            }
//            return min_finish_time;
        }
        else return -1;
    };
    int getEventsSize(){
        return events.size();
    }
    int getNextEventStateTS(){
        return events.front().my_process->state_timestamp;
    };
    //for adding io burst, the timestamp right before the next transition is now+io
    void addEvent(Process * p, int timestamp, transition tran){
        //CREATE EVENT
        Event evt( p, timestamp, tran);
        //ADD TO EVENTQUEUE
        events.push_back(evt);
    };
    void addEvent(Process * p, int now, int rcb, transition tran){

    }
};


#endif //SCHEDULER_EVENT_H
