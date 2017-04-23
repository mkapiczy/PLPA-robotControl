(include "FloorUtil.scm")

(define (robot x y direction errorCode)
  (letrec (
           (getX    (lambda () x))
           (getY    (lambda () y))
           (getDirection (lambda () direction))
           (getErrorCode (lambda () errorCode))
           (moveForward (lambda ()
                          (if (isMovementAllowed "FORWARD")
                              (robot
                               (getNextXPosition "FORWARD")
                               (getNextYPosition "FORWARD")
                               direction
                               errorCode)
                              (robot
                               x
                               y
                               direction
                               -1))
                          ))
           (turnRight (lambda ()
                        (robot
                         x
                         y
                         (getNextDirection "RIGHT")
                         errorCode)))
           (turnLeft (lambda ()
                       (robot
                        x
                        y
                        (getNextDirection "LEFT")
                        errorCode)))

           (type-of (lambda () 'robot))


           (getNextXPosition (lambda (movementDirection)
                               (if (eq? "FORWARD" movementDirection)
                                   (cond
                                     ((eq? "N" direction) x)
                                     ((eq? "E" direction) (+ x 1))
                                     ((eq? "S" direction) x)
                                     ((eq? "W" direction) (- x 1))
                                     )
                                   (x)
                                   )))
           (getNextYPosition (lambda (movementDirection)
                               (if (eq? "FORWARD" movementDirection)
                                   (cond
                                     ((eq? "N" direction) (- y 1))
                                     ((eq? "E" direction) y)
                                     ((eq? "S" direction) (+ y 1))
                                     ((eq? "W" direction) y)
                                     )
                                   (x)
                                   )))

           (getNextDirection (lambda (rotationDirection)
                               (if (eq? rotationDirection "RIGHT")
                                   (cond
                                     ((eq? "N" direction) "E")
                                     ((eq? "E" direction) "S")
                                     ((eq? "S" direction) "W")
                                     ((eq? "W" direction) "N"))
                                   (cond
                                     ((eq? "N" direction) "W")
                                     ((eq? "W" direction) "S")
                                     ((eq? "S" direction) "E")
                                     ((eq? "E" direction) "N"))
                                   )
                               ))

           (isMovementAllowed (lambda (movementDirection)
                                (
                                   (if ((cond
                                         ((eq? (get-item (getNextXPosition movementDirection) (getNextYPosition movementDirection)) "A") #t)
                                         ((eq? (get-item (getNextXPosition movementDirection) (getNextYPosition movementDirection)) "P") #t)
                                         ((eq? (get-item (getNextXPosition movementDirection) (getNextYPosition movementDirection)) "i") #t)
                                         ((eq? (get-item (getNextXPosition movementDirection) (getNextYPosition movementDirection)) "o") #t)
                                         ((eq? (get-item (getNextXPosition movementDirection) (getNextYPosition movementDirection)) "*") #t)
                                         ))
                                       #t
                                       #f
                                       ))))
           )


           (lambda (message)
             (cond ((eq? message 'getX) getX)
                   ((eq? message 'getY) getY)
                   ((eq? message 'getDirection)  getDirection)
                   ((eq? message 'moveForward)  moveForward)
                   ((eq? message 'turnRight)  turnRight)
                   ((eq? message 'turnLeft)  turnLeft)
                   ((eq? message 'type-of) type-of)
                   (else (error "Message not understood"))))
           )
    )




  (define (send message object . args)
    (let ((method (object message)))
      (cond ((procedure? method) (apply method args))
            (else (error "Error in method lookup " method)))))
