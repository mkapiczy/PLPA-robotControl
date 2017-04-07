(define get-item
    (lambda (2d-vct x y)
        (vector-ref (vector-ref 2d-vct x) y)
    )
)