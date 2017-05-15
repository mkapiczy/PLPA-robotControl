;(include "FloorPlan.scm") ;Uncomment this for testing in PetiteScheme


(define getTile (lambda (x y)
                  (if (and (>= y 0) (< y (vector-length factoryFloor)))
                      (if (and (>= x 0) (< x (vector-length (vector-ref factoryFloor y))))
                         (vector-ref (vector-ref factoryFloor y) x)
                         '()
                         )
                      '()
                      )
                  )
 )


(define getFactoryFloor
	(lambda() factoryFloor)
)
