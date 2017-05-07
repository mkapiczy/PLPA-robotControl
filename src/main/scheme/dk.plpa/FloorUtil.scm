;(include "FloorPlan.scm") ;Uncomment this for testing in PetiteScheme

(define getTile
    (lambda (x y)
        (vector-ref (vector-ref factoryFloor y) x)
    )
)

(define getFactoryFloor
	(lambda() factoryFloor)
)
