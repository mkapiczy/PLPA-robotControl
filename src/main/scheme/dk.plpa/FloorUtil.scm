;(include "FloorPlan.scm") UNCOMMENT THIS WHEN TESTING FROM PETIT INTERPRETER
(define getTile
    (lambda (x y)
        (vector-ref (vector-ref factoryFloor y) x)
    )
)

(define getFactoryFloor
	(lambda() factoryFloor)
)