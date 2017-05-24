(import  
 (rough-draft unit-test)
 (rough-draft console-test-runner)) 

(include "../robot.scm")

(define-test-suite robotTestSuite
  
  (define-test robotMoveForward-test
    (let ((robot (robot 0 8 "E" 0 '())))
      (let ((newRobot (send 'moveForward robot 8)))
        (assert-eqv? (send 'getX newRobot) 8)
        (assert-eqv? (send 'getY newRobot) 8)
        (assert-eqv? (send 'getErrorCode newRobot) 0)
        )
      ))
  )

(run-test-suite robotTestSuite)

