module.exports = {
    mode: 'development',
    devtool: false,
    devServer: {
        disableHostCheck: true,
        port: process.env.PORT ? process.env.PORT : 3000,
        historyApiFallback: true,
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