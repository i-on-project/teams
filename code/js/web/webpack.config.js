module.exports = {
    mode: 'development',
    devtool: false,
    devServer: {
        port: 3000,
        proxy: [
            {
              context: ['/auth', '/api'],
              target: 'http://localhost:8080',
            },
          ],
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