module.exports = {
    mode: 'development',
    devtool: false,
    devServer: {
        port: 3030,
        hot: false
    },
    resolve: {
        extensions: ['.js', '.ts', '.tsx'],
    },
    module: {
        rules: [
            {
                test: /\.tsx?$/,
                use: 'ts-loader',
                exclude: /node_modules/,
            },
        ],
    },
}