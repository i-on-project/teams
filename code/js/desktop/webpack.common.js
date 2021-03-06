const path = require('path');
   
module.exports = {
    mode: 'development',
    entry: './src/index.tsx',
    devtool: false,
    devServer: {
        hot: false
    },
    resolve: {
        extensions: ['.js', '.ts', '.tsx'],
        fallback: { "path": false }
    },
    module: {
        rules: [
            {
                test: /\.tsx?$/,
                use: 'ts-loader',
                exclude: /node_modules/,
            },
            {
                test: /\.css$/i,
                use: ["style-loader", "css-loader"],
            },
        ],
    },
    output: {
        filename: 'bundle.js',
        path: path.resolve(__dirname, 'dist'),
    },
}