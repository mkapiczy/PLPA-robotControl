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
                               (if (equal? "FORWARD" movementDirection)
                                   (cond
                                     ((equal? "N" direction) x)
                                     ((equal? "E" direction) (+ x 1))
                                     ((equal? "S" direction) x)
                                     ((equal? "W" direction) (- x 1))
                                     )
                                   x
                                   )))
           (getNextYPosition (lambda (movementDirection)
                               (if (equal? "FORWARD" movementDirection)
                                   (cond
                                     ((equal? "N" direction) (- y 1))
                                     ((equal? "E" direction) y)
                                     ((equal? "S" direction) (+ y 1))
                                     ((equal? "W" direction) y)
                                     )
                                   y
                                   )))

           (getNextDirection (lambda (rotationDirection)
                               (if (equal? rotationDirection "RIGHT")
                                   (cond
                                     ((equal? "N" direction) "E")
                                     ((equal? "E" direction) "S")
                                     ((equal? "S" direction) "W")
                                     ((equal? "W" direction) "N"))
                                   (cond
                                     ((equal? "N" direction) "W")
                                     ((equal? "W" direction) "S")
                                     ((equal? "S" direction) "E")
                                     ((equal? "E" direction) "N"))
                                   )
                               ))

           (isMovementAllowed (lambda (movementDirection)
            (cond
              ((equal? 'A (get-item (getNextXPosition movementDirection) (getNextYPosition movementDirection))) #t)
              ((equal? 'P (get-item (getNextXPosition movementDirection) (getNextYPosition movementDirection))) #t)
              ((equal? 'o (get-item (getNextXPosition movementDirection) (getNextYPosition movementDirection))) #t)
              ((equal? '* (get-item (getNextXPosition movementDirection) (getNextYPosition movementDirection))) #t)
              ((equal? 'i (get-item (getNextXPosition movementDirection) (getNextYPosition movementDirection))) #t)
              (else #f)
            )
           ))
           )


           (lambda (message)
             (cond ((eq? message 'getX) getX)
                   ((eq? message 'getY) getY)
                   ((eq? message 'getDirection)  getDirection)
                   ((eq? message 'moveForward)  moveForward)
                   ((eq? message 'turnRight)  turnRight)
                   ((eq? message 'turnLeft)  turnLeft)
                    ((eq? message 'getErrorCode)  getErrorCode)
                   ((eq? message 'type-of) type-of)
                   (else (error "Message not understood"))))
           )
    )




  (define (send message object . args)
    (let ((method (object message)))
      (cond ((procedure? method) (apply method args))
            (else (error "Error in method lookup " method)))))
