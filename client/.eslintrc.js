module.exports = {
  env: {
    browser: true,
    es2021: true
  },
  extends: [
    'eslint:recommended',
    'plugin:react/recommended',
    'eslint-config-prettier'
  ],
  parserOptions: {
    ecmaFeatures: {
      jsx: true
    },
    ecmaVersion: 2020,
    sourceType: 'module'
  },
  plugins: ['react', 'prettier'],
  rules: {
    indent: ['error', 4],
    quotes: ['error', 'single'],
    semi: ['error', 'always'],
    'linebreak-style': ['error', 'windows'],
    'react/jsx-closing-bracket-location': [2, 'tag-aligned'],
    'react/jsx-first-prop-new-line': [2, 'multiline'],
    'react/jsx-indent-props': [2, 2],
    'react/jsx-max-props-per-line': [2, { maximum: 1, when: 'multiline' }]
  }
};
