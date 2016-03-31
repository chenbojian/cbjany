module.exports = {
    module: {
        loaders: [{
            test: /\.es6?$/,
            exclude: /(node_modules|bower_components)/,
            loader: 'babel',
            query: {
                presets: ['es2015']
            }
        }]
    },
    entry: './src/app.es6',
    output: {
        filename: 'bundle.js',
        path: './dist'
    }
}
