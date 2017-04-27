;;Robot simulation
;;(include "robot.scm")

(define commands '())

(define (loadCommands values)
    (set! commands values))

(define (printGlobalValues)
    (for-each (lambda (commands)
    (display commands)
    (newline))
    commands))

;;(define robotState
;;      (lambda (init)
;;          (let* ((state init))
;;              (case-lambda
;;                ((aproc) (set! state aproc))
;;                ((aproc bsteps) (set! state aproc)))
;;               )))

;; THIS SHOULD WORK BUT DOESNT.. Sucks
(define robotState
      (lambda (init)
          (let ((state init))
              (lambda (proc value)
                  (set! state ((proc state value)))
                    state))))


(define (MoveForward state noOfSteps)
    (send 'moveForward state noOfSteps)
)

(define (GetX state)
    (send 'getX state)
)



(define r (robot 8 5 "S" 0 '()))

(define step (robotState r))



;; WORKING EXAMPLE

(define robotState2
      (lambda (init)
          (let ((state init))
              (case-lambda
                ((a) a)
                ((a b) (+ a b))
               ))))


(define step (robotState2 2))
