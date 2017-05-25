const path = require('path');

module.exports = {
	entry: './app/app.ts',
	output: {
    	path: path.resolve(__dirname, "dist"),
    	filename: "bundle.js",
	},
	module: {
		rules: [
			{
				test: /\.ts$/,
				include: [path.resolve(__dirname, "app")],
				loader: "awesome-typescript-loader"
			},
		],
	},
	resolve: {
		extensions: ['.ts', '.js'],
	}
	
};