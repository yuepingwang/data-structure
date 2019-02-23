//
// Created by Yueping Wang on 6/27/18.
//

#ifndef SCHEDULER_ENUMS_H
#define SCHEDULER_ENUMS_H
#include <string>
#include<list>
#include <iostream>
enum transition {
    TRANS_TO_READY,
    TRANS_TO_RUN,
    TRANS_TO_BLOCK,
    TRANS_TO_PREEMPT,
};

enum state {
    CREATED,
    READY,
    RUNNG,
    BLOCK,
    Done,
};
#endif //SCHEDULER_ENUMS_H
