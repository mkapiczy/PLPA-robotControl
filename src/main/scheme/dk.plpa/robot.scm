;(include "FloorUtil.scm") ;UNCOMMENT THIS WHEN TESTING FROM PETIT INTERPRETER
;(include "../FloorUtil.scm") 
(define (robot x y direction errorCode carriedObject)
  (letrec (
           (getX (lambda () x))

           (getY (lambda () y))

           (getDirection (lambda () direction))

           (getErrorCode (lambda () errorCode))

           (getCarriedObject (lambda () carriedObject))

           (getRobotStateAsList (lambda ()
               (list x y direction errorCode carriedObject)))

           (moveForward (lambda (amountOfSteps)
                          (if (> amountOfSteps 0)
                              (send 'moveForward (moveForwardOneStep) (- amountOfSteps 1))
                              (self)
                              )))

           (moveForwardOneStep (lambda()
                                 (if (isMovementAllowed "FORWARD")
                                     (begin
                                       (display (string-append "Robot moved forward from " (number->string x) " " (number->string y) " to " (number->string (getNextXPosition "FORWARD")) " "  (number->string (getNextYPosition "FORWARD"))))
                                       (robot
                                        (getNextXPosition "FORWARD")
                                        (getNextYPosition "FORWARD")
                                        direction
                                        errorCode
                                        carriedObject))
                                     (begin
                                       (display (string-append "Movement forward from " (number->string x) " " (number->string y) " not allowed"))
                                       (selfWithError)))
                                 ))

         (turnRight (lambda (amountOfSteps)
                        (if (> amountOfSteps 0)
                            (send 'turnRight (turnRightOneStep) (- amountOfSteps 1))
                            (self)
                            )))

           (turnRightOneStep (lambda ()
                        (display (string-append "Robot turned right from " direction " to " (getNextDirection "RIGHT")))
                        (robot
                         x
                         y
                         (getNextDirection "RIGHT")
                         errorCode
                         carriedObject)))

         (turnLeft (lambda (amountOfSteps)
                        (if (> amountOfSteps 0)
                            (send 'turnLeft (turnLeftOneStep) (- amountOfSteps 1))
                            (self)
                            )))

           (turnLeftOneStep (lambda ()
                       (display (string-append "Robot turned left from " direction " to " (getNextDirection "LEFT")))
                       (robot
                        x
                        y
                        (getNextDirection "LEFT")
                        errorCode
                        carriedObject)))

           (pickObject (lambda (objectToPick)
                         (if (canPickObject)
                             (begin
                               (display (string-append "Robot picked object " objectToPick))
                               (robot
                                x
                                y
                                direction
                                errorCode
                                objectToPick))
                                (begin
                             (display (string-append "Robot can not pick object " objectToPick))
                                (selfWithError))
                         )))

           (dropObject (lambda ()
                         (if (canDropObject)
                             (begin
                               (display (string-append "Robot dropped object " carriedObject))
                               (robot
                                x
                                y
                                direction
                                errorCode
                                '()))
                            (begin
                             (display (string-append "Robot can not drop object - there is no carried object or the position does not allow for droping of objects"))
                             (selfWithError))
                             )
                         )
                       )

           (turnOff (lambda ()
                (robot
                    x
                    y
                    direction
                    1
                    carriedObject)
                    ))

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
                           ((equal? 'A (getTile (getNextXPosition movementDirection) (getNextYPosition movementDirection))) #t)
                           ((equal? 'P (getTile (getNextXPosition movementDirection) (getNextYPosition movementDirection))) #t)
                           ((equal? 'o (getTile (getNextXPosition movementDirection) (getNextYPosition movementDirection))) #t)
                           ((equal? '* (getTile (getNextXPosition movementDirection) (getNextYPosition movementDirection))) #t)
                           ((equal? 'i (getTile (getNextXPosition movementDirection) (getNextYPosition movementDirection))) #t)
                           (else #f)
                           )
                         ))


    (canPickObject (lambda ()
                     (if (and (null? carriedObject) (or (equal? 'i (getTile x y)) (equal? '* (getTile x y))))
                        #t
                        #f
                        )
                     ))

    (canDropObject (lambda ()
                (if (and (not (null? carriedObject)) (or (equal? 'o (getTile x y)) (equal? '* (getTile x y))))
                    #t
                    #f
                    )
                     ))

    (self (lambda ()
            (robot
             x
             y
             direction
             errorCode
             carriedObject)
            ))

    (selfWithError (lambda ()
            (robot
             x
             y
             direction
             -1
             carriedObject)
            ))

    (type-of (lambda () 'robot))
    )




  (lambda (message)
    (cond ((eq? message 'getX) getX)
          ((eq? message 'getY) getY)
          ((eq? message 'getDirection)  getDirection)
          ((eq? message 'getCarriedObject) getCarriedObject)
          ((eq? message 'moveForward)  moveForward)
          ((eq? message 'turnRight)  turnRight)
          ((eq? message 'turnLeft)  turnLeft)
          ((eq? message 'turnOff) turnOff)
          ((eq? message 'getErrorCode)  getErrorCode)
          ((eq? message 'pickObject)  pickObject)
          ((eq? message 'dropObject)  dropObject)
          ((eq? message 'type-of) type-of)
          ((eq? message 'getRobotStateAsList) getRobotStateAsList)
          (else (error "Message not understood"))))
  )
)




(define (send message object . args)
  (let ((method (object message)))
    (cond ((procedure? method) (apply method args))
          (else (error "Error in method lookup " method)))))
