module.exports = {
  extends: [
    'plugin:react/recommended',
    'plugin:@typescript-eslint/recommended',
    'plugin:prettier/recommended',
    'eslint-config-prettier',
    'react-app'
  ],
  plugins: ['react', '@typescript-eslint', 'prettier'],
  env: {
    node: true
  },
  parser: '@typescript-eslint/parser',
  settings: {
    'import/parsers': {
      '@typescript-eslint/parser': ['.ts', '.tsx']
    },
    'import/resolver': {
      typescript: {}
    },
    react: {
      version: 'detect'
    }
  },
  parserOptions: {
    project: './tsconfig.json',
    tsconfigRootDir: './',
    sourceType: 'module',
    ecmaVersion: 2020,
    ecmaFeatures: { jsx: true, legacyDecorators: true }
  },
  rules: {
    'react/jsx-uses-react': 'off',
    '@typescript-eslint/explicit-function-return-type': 1,
    'react/react-in-jsx-scope': 'off',
    '@typescript-eslint/ban-ts-comment': 'off',
    '@typescript-eslint/explicit-function-return-type': 2,
    '@typescript-eslint/no-explicit-any': 1,
    '@typescript-eslint/no-non-null-assertion': 0,
    'eslint/no-throw-literal': 'off',
    'react-hooks/exhaustive-deps': 'off',
    'react/jsx-closing-bracket-location': [2, 'tag-aligned'],
    'react/jsx-first-prop-new-line': [2, 'multiline'],
    'react/jsx-indent-props': [2, 2],
    'react/jsx-max-props-per-line': [2, { maximum: 1, when: 'multiline' }],
    'react/no-unescaped-entities': 0,
    'react/prop-types': 0
  }
};
