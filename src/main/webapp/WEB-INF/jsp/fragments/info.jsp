<style style="text/css">
    .scroll {
        color: white;
        height: 50px;
        overflow: hidden;
        position: relative;
    }
    .scroll h3 {
        color: white;
        position: absolute;
        width: 100%;
        height: 100%;
        margin: 0;
        line-height: 50px;
        text-align: center;
        /* Starting position */
        -moz-transform:translateX(100%);
        -webkit-transform:translateX(100%);
        transform:translateX(100%);
        /* Apply animation to this element */
        -moz-animation: move 10s linear infinite;
        -webkit-animation: move 10s linear infinite;
        animation: move 10s linear infinite;
    }
    /* Move it (define the animation) */
    @-moz-keyframes move {
        0%   { -moz-transform: translateX(100%); }
        100% { -moz-transform: translateX(-100%); }
    }
    @-webkit-keyframes move {
        0%   { -webkit-transform: translateX(100%); }
        100% { -webkit-transform: translateX(-100%); }
    }
    @keyframes move {
        0%   {
            -moz-transform: translateX(100%); /* Firefox bug fix */
            -webkit-transform: translateX(100%); /* Firefox bug fix */
            transform: translateX(100%);
        }
        100% {
            -moz-transform: translateX(-100%); /* Firefox bug fix */
            -webkit-transform: translateX(-100%); /* Firefox bug fix */
            transform: translateX(-100%);
        }
    }
</style>